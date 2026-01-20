package com.library.inventory.controller;

import com.library.inventory.dto.ReservationRequest;
import com.library.inventory.model.Book;
import com.library.inventory.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books") // Base path for all book-related endpoints
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // POST create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book created = bookService.createBook(book);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // STEP 4: Reader reserves a book
    @PostMapping("/{id}/reserve")
    public ResponseEntity<Book> reserveBook(
            @PathVariable Long id,
            @RequestBody ReservationRequest request) {

        Book reserved = bookService.reserveBook(id, request);
        return ResponseEntity.ok(reserved);
    }

    // STEP 5: Admin view of reservations
    @GetMapping("/admin/reservations")
    public ResponseEntity<List<Book>> getReservedBooks() {
        return ResponseEntity.ok(bookService.getAllReservedBooks());
    }
}





