package com.library.inventory.service;

import com.library.inventory.model.Book;
import com.library.inventory.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() { return repository.findAll(); }
    public Book addBook(Book book) { return repository.save(book); }
    public Book updateBook(Book book) { return repository.save(book); }
    public void deleteBook(Long id) { repository.deleteById(id); }
}
