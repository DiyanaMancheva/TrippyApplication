package com.diyanamancheva.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
  public ResponseEntity<AuthorDto> getAuthor(@PathVariable int id) {
    Author author = authorService.getAuthorById(id);
    AuthorDto authorDto = new AuthorDto(author.getId(),author.getName());
    return ResponseEntity.ok(authorDto);
  }

  @PostMapping("/authors")
  public ResponseEntity<Void> createAuthor(@RequestBody AuthorRequest authorRequest){
    Author author = authorService.addAuthor(authorRequest.getName());
    URI location = UriComponentsBuilder.fromUriString("/authors/{id}")
                                       .buildAndExpand(author.getId())
                                       .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/authors/{id}")
  public ResponseEntity<AuthorDto> updateAuthor(
    @RequestBody AuthorRequest authorRequest, @PathVariable int id,
    @RequestParam(required = false) boolean returnOld) {

    AuthorDto authorDto = authorService.editAuthor(id, authorRequest);
    if (returnOld) {
      return ResponseEntity.ok(authorDto);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
  @DeleteMapping("authors/{id}")
  public ResponseEntity<AuthorDto> removeAuthor(
    @RequestBody AuthorRequest authorRequest, @PathVariable int id,
    @RequestParam(required = false) boolean returnOld){

    AuthorDto authorDto = authorService.removeAuthor(id);
    if(returnOld){
      return ResponseEntity.ok(authorDto);
    }else{
      return ResponseEntity.noContent().build();
    }
  }
}
