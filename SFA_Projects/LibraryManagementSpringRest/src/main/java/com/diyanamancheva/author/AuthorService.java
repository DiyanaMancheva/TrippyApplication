package com.diyanamancheva.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AuthorService {
  private AuthorAccessor authorAccessor;
  private AuthorMapper authorMapper;

  @Autowired
  public AuthorService(AuthorAccessor authorAccessor, AuthorMapper authorMapper) {
    this.authorAccessor = authorAccessor;
    this.authorMapper = authorMapper;
  }

  public List<AuthorDto> getAllAuthors() {
    List<Author> authors = authorAccessor.readAllAuthors();

    return authorMapper.mapAuthorsToDtos(authors);
  }

  public void addAuthor(String name) {
    Author author = new Author(name);
    authorAccessor.addAuthor(author);
  }

  public int editAuthor(int id, String name) {

    Author author = new Author(name);

    author.setId(id);

    return authorAccessor.updateAuthor(author);
  }

  public int deleteAuthor(int id) {
    return authorAccessor.deleteAuthor(id);
  }
  public Author getAuthorByName(String name, List<Author> authors) {
    Author author = null;
    for (Author authorCurr : authors) {
      if (authorCurr.getName().equals(name)) {
        author = authorCurr;
        break;
      }
    }
    return author;
  }
}
