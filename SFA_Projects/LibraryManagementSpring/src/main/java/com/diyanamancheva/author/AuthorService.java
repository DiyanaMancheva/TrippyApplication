package com.diyanamancheva.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
  private final AuthorAccessor authorAccessor;

  @Autowired
  public AuthorService(AuthorAccessor authorAccessor) {
    this.authorAccessor = authorAccessor;
  }

  public void addAuthor(String name) {
    Author author = new Author(name);
    authorAccessor.addAuthor(author);
  }

  public int deleteAuthor(int id) {
    return authorAccessor.deleteAuthor(id);
  }

  public Author getAuthorByName(String name, List<Author> authors) {
    Author author = null;
    for (Author authorFromList : authors) {
      if (authorFromList.getName().equals(name)) {
        author = authorFromList;
        break;
      }
    }
    return author;
  }

  public List<Author> getAllAuthors() {
    return authorAccessor.readAllAuthors();
  }
}
