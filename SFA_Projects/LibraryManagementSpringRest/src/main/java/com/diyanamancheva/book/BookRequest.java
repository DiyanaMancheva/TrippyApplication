package com.diyanamancheva.book;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class BookRequest {
  @Pattern(regexp = "[A-Za-z0-9\\s.]+", message = "Name must not be null or contain numbers")
  private String title;
  @Positive(message = "AuthorId must be greater than 0")
  private int authorId;
  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", message = "PublishingDate must be in dd/mm/yyyy format")
  private String publishingDate;

  public BookRequest(String title, int authorId, String publishingDate) {
    this.title = title;
    this.authorId = authorId;
    this.publishingDate = publishingDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public String getPublishingDate() {
    return publishingDate;
  }

  public void setPublishingDate(String publishingDate) {
    this.publishingDate = publishingDate;
  }
}
