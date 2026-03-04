package com.library.inventory.dto;

import com.library.inventory.model.BookStatus;
import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String category;
    private BookStatus status;
    private String reservedBy;
    private LocalDate reservationStart;
    private LocalDate reservationEnd;
    private String coverUrl;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    public String getReservedBy() { return reservedBy; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }
    public LocalDate getReservationStart() { return reservationStart; }
    public void setReservationStart(LocalDate reservationStart) { this.reservationStart = reservationStart; }
    public LocalDate getReservationEnd() { return reservationEnd; }
    public void setReservationEnd(LocalDate reservationEnd) { this.reservationEnd = reservationEnd; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
}