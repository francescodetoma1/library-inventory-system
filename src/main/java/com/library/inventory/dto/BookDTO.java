package com.library.inventory.dto;

import com.library.inventory.model.BookStatus;

public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private BookStatus status;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
