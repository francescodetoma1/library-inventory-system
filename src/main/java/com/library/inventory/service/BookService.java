package com.library.inventory.service;

import com.library.inventory.dto.BookDTO;
import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import com.library.inventory.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = findBookOrThrow(id);
        return mapToDTO(book);
    }

    public BookDTO createBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setStatus(BookStatus.AVAILABLE);

        return mapToDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = findBookOrThrow(id);

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setStatus(dto.getStatus());

        return mapToDTO(bookRepository.save(book));
    }

    public BookDTO reserveBook(Long id) {
        Book book = findBookOrThrow(id);

        validateReservation(book);

        book.setStatus(BookStatus.RESERVED);
        return mapToDTO(bookRepository.save(book));
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    private void validateReservation(Book book) {
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book not available");
        }
    }

    private BookDTO mapToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getStatus()
        );
    }
}
