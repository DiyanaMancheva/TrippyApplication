package com.diyanamancheva.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/index")
  public ResponseEntity<String> getIndex() {
    return ResponseEntity.ok("Welcome to LibraryManagement Application!");
  }

  @GetMapping("/books")
  public ResponseEntity<List<BookDto>> printAllBooks() {
    List<BookDto> books = bookService.getAllBooks();
    return ResponseEntity.ok(books);
  }

  @PostMapping("/books")
  public ResponseEntity<Void> createBook(@RequestBody BookRequest bookRequest) {
    bookService.addBook(bookRequest.getTitle(), bookRequest.getAuthorId(), bookRequest.getPublishingDate());
    return ResponseEntity.status(201).build();
  }
}
