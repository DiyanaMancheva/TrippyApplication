package com.diyanamancheva.book;

public class BookRequest {
  private String title;
  private int authorId;
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
