package com.diyanamancheva.author;

import com.diyanamancheva.client.Client;
import com.diyanamancheva.client.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/authors/{id}")
  public ResponseEntity<AuthorDto> getSuthor(@PathVariable int id) {
    Author author = authorService.getAuthorById(id);
    AuthorDto authorDto = new AuthorDto(author.getId(),author.getName());
    return ResponseEntity.ok(authorDto);
  }

  @PostMapping("/authors")
  public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequest authorRequest){
    authorService.addAuthor(authorRequest.getName());
    return ResponseEntity.status(201).build();
  }
}
