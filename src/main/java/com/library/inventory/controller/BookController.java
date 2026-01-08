package com.library.inventory.controller;

import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import com.library.inventory.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getStatus() != null ? book.getStatus() : BookStatus.AVAILABLE
        );
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    // Update existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(
                id,
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getStatus()
        );
        if (updatedBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Reserve a book
    @PostMapping("/{id}/reserve")
    public ResponseEntity<Book> reserveBook(@PathVariable Long id, @RequestParam String reservedBy) {
        Book reservedBook = bookService.reserveBook(id, reservedBy);
        if (reservedBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservedBook, HttpStatus.OK);
    }

    // Make a book available
    @PostMapping("/{id}/available")
    public ResponseEntity<Book> makeBookAvailable(@PathVariable Long id) {
        Book availableBook = bookService.makeBookAvailable(id);
        if (availableBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(availableBook, HttpStatus.OK);
    }
}




