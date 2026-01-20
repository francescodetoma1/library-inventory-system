package com.library.inventory.dto;

import java.time.LocalDate;

public class ReservationRequest {

    private String reader;
    private LocalDate startDate;
    private LocalDate endDate;

    public ReservationRequest() {}

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
