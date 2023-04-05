package com.diyanamancheva.author;

import java.util.List;

public class AuthorMapper {
  public Author mapStringToAuthor(String string) {
    String[] tokens = string.split(" ");
    int id = Integer.parseInt(tokens[0]);
    StringBuilder nameBuilder = new StringBuilder();
    for (int i = 1; i < tokens.length; i++) {
      if (i < tokens.length - 1) {
        nameBuilder.append(tokens[i]).append(" ");
      } else {
        nameBuilder.append(tokens[i]);
      }
    }
    String name = nameBuilder.toString();

    Author author = new Author(name);
    author.setId(id);

    return author;
  }

  public String mapAuthorToString(Author author){
    int idtString = author.getId();
    String nameString = author.getName();

    return String.join(" ", Integer.toString(idtString), nameString);
  }

  public String mapAuthorListToString(List<Author> authorList){
    StringBuilder authorStringBuilder = new StringBuilder();
    for (Author author: authorList) {
      String authorString = mapAuthorToString(author);
      authorStringBuilder.append(authorString).append("\n");
    }

    return authorStringBuilder.toString();
  }
}
