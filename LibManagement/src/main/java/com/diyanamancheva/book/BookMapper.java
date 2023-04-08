package com.diyanamancheva.book;

import com.diyanamancheva.author.Author;

import java.util.List;

public class BookMapper {
  public Book mapStringToBook(String string) {
    String[] parts = string.split("_");
    String bookPart = parts[0];
    String authorPart = parts[1];
    String datePart = parts[2];

    String[] bookTokens = bookPart.split(" ");
    int bookId = Integer.parseInt(bookTokens[0]);
    StringBuilder bookNameBuilder = new StringBuilder();
    for (int i = 1; i < bookTokens.length; i++) {
      if (i < bookTokens.length - 1) {
        bookNameBuilder.append(bookTokens[i]).append(" ");
      } else {
        bookNameBuilder.append(bookTokens[i]);
      }
    }
    String bookName = bookNameBuilder.toString();

    String[] authorTokens = authorPart.split(" ");
    StringBuilder authorNameBuilder = new StringBuilder();
    for (int i = 1; i < authorTokens.length; i++) {
      if (i < authorTokens.length - 1) {
        authorNameBuilder.append(authorTokens[i]).append(" ");
      } else {
        authorNameBuilder.append(authorTokens[i]);
      }
    }
    String authorName = authorNameBuilder.toString();
    Author author = new Author(authorName);

    Book book = new Book(bookName, author, datePart);
    book.setId(bookId);

    return book;
  }

  public String mapBookToString(Book book){
    int idString = book.getId();
    String nameString = book.getName();
    String authorString = book.getAuthor().getName();
    String publishingDate = book.getPublishingDate();

    String BookNameString = String.join(" ", Integer.toString(idString), nameString);
    return String.join("_", BookNameString, authorString, publishingDate);
  }

  public String mapBookListToString(List<Book> bookList){
    StringBuilder bookStringBuilder = new StringBuilder();
    for (Book book: bookList) {
      String bookString = mapBookToString(book);
      bookStringBuilder.append(bookString).append("\n");
    }

    return bookStringBuilder.toString();
  }
}
