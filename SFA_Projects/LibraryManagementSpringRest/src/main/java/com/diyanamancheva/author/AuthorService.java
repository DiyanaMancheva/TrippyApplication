package com.diyanamancheva.author;

import com.diyanamancheva.client.Client;
import com.diyanamancheva.client.ClientDto;
import com.diyanamancheva.client.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

  public Author getAuthorById(int id) {
    return authorAccessor.readAuthorById(id);
  }

  public Author addAuthor(String name) {
    Author author = new Author(name);
    author = authorAccessor.addAuthor(author);
    return author;
  }

  public AuthorDto editAuthor(int id, AuthorRequest authorRequest) {

    Author author = getAuthorById(id);
    Author authorNew = new Author(id, authorRequest.getName());
    authorAccessor.updateAuthor(authorNew);
    AuthorDto authorDto = new AuthorDto(author.getId(), author.getName());

    return authorDto;
  }

  public AuthorDto removeAuthor(int id) {
    Author authorOld = getAuthorById(id);
    authorAccessor.deleteAuthor(id);
    return new AuthorDto(authorOld.getId(), authorOld.getName());
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
