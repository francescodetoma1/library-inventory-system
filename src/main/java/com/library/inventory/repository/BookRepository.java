package com.library.inventory.repository;

import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Returns all books with the given status
    List<Book> findByStatus(BookStatus status);
}


