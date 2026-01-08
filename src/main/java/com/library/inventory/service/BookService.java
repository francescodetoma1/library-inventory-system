package com.library.inventory.service;

import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import com.library.inventory.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieve book by id
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Add new book
    public Book addBook(String title, String author, String category, BookStatus status) {
        Book book = new Book(title, author, category, status);
        return bookRepository.save(book);
    }

    // Update book details
    public Book updateBook(Long id, String title, String author, String category, BookStatus status) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            book.setStatus(status);
            return bookRepository.save(book);
        }
        return null;
    }

    // Delete a book
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Set book as reserved
    public Book reserveBook(Long id, String reservedBy) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus(BookStatus.RESERVED);
            book.setReservedBy(reservedBy);
            return bookRepository.save(book);
        }
        return null;
    }

    // Set book as available
    public Book makeBookAvailable(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus(BookStatus.AVAILABLE);
            book.setReservedBy(null);
            book.setReservationStart(null);
            book.setReservationEnd(null);
            return bookRepository.save(book);
        }
        return null;
    }

    // Additional methods for overdue or lost books can be added here
}
