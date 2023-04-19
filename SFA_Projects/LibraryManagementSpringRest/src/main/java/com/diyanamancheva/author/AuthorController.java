package com.diyanamancheva.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {
  private AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService){
    this.authorService = authorService;
  }

  @GetMapping("/authors")
  public ResponseEntity<List<AuthorDto>> printAllAuthors() {
    List<AuthorDto> authors = authorService.getAllAuthors();
    return ResponseEntity.ok(authors);
  }

  @PostMapping("/authors")
  public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequest authorRequest){
    authorService.addAuthor(authorRequest.getName());
    return ResponseEntity.status(201).build();
  }
}
