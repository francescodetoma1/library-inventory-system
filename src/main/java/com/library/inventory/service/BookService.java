package com.library.inventory.service;

import com.library.inventory.dto.ReservationRequest;
import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import com.library.inventory.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null) {
            throw new IllegalArgumentException("Title and author are required");
        }
        book.setStatus(BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    // STEP 4: Reserve a book
    public Book reserveBook(Long bookId, ReservationRequest request) {

        Book book = getBookById(bookId);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for reservation");
        }

        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Reservation dates are required");
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        book.setStatus(BookStatus.RESERVED);
        book.setReservedBy(request.getReader());
        book.setReservationStart(request.getStartDate());
        book.setReservationEnd(request.getEndDate());

        return bookRepository.save(book);
    }

    // STEP 5: Admin view
    public List<Book> getAllReservedBooks() {
        return bookRepository.findByStatus(BookStatus.RESERVED);
    }
}
