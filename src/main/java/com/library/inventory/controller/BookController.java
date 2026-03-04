package com.library.inventory.controller;

import com.library.inventory.dto.BookDTO;
import com.library.inventory.dto.ReservationRequest;
import com.library.inventory.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<BookDTO> reserveBook(@PathVariable Long id,
                                               @RequestBody ReservationRequest request,
                                               Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(bookService.reserveBook(id, username, request.getStartDate(), request.getEndDate()));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookDTO> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.cancelReservation(id));
    }
}





