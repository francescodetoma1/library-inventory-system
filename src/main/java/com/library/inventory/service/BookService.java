package com.library.inventory.service;

import com.library.inventory.dto.BookDTO;
import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import com.library.inventory.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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

    public List<BookDTO> searchBooks(String query) {
        return bookRepository.findAll()
                .stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(query.toLowerCase())
                        || (b.getCategory() != null && b.getCategory().toLowerCase().contains(query.toLowerCase())))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        return mapToDTO(findBookOrThrow(id));
    }

    public BookDTO createBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setStatus(BookStatus.AVAILABLE);
        book.setCoverUrl(generateCoverUrl(dto.getTitle()));
        return mapToDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = findBookOrThrow(id);
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setStatus(dto.getStatus());
        return mapToDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO reserveBook(Long id, String username, LocalDate startDate, LocalDate endDate) {
        Book book = findBookOrThrow(id);
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book not available");
        }
        book.setStatus(BookStatus.RESERVED);
        book.setReservedBy(username);
        book.setReservationStart(startDate);
        book.setReservationEnd(endDate);
        return mapToDTO(bookRepository.save(book));
    }

    public BookDTO cancelReservation(Long id) {
        Book book = findBookOrThrow(id);
        book.setStatus(BookStatus.AVAILABLE);
        book.setReservedBy(null);
        book.setReservationStart(null);
        book.setReservationEnd(null);
        return mapToDTO(bookRepository.save(book));
    }

    private String generateCoverUrl(String title) {
        String encoded = title.trim().replace(" ", "+");
        return "https://covers.openlibrary.org/b/title/" + encoded + "-M.jpg";
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getStatus());
        dto.setCategory(book.getCategory());
        dto.setReservedBy(book.getReservedBy());
        dto.setReservationStart(book.getReservationStart());
        dto.setReservationEnd(book.getReservationEnd());
        dto.setCoverUrl(book.getCoverUrl() != null ? book.getCoverUrl() : generateCoverUrl(book.getTitle()));
        return dto;
    }
}