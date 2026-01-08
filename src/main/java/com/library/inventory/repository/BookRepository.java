package com.library.inventory.repository;

import com.library.inventory.model.Book;
import com.library.inventory.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Trova tutti i libri per stato
    List<Book> findByStatus(BookStatus status);

    // Trova libri riservati da un determinato utente
    List<Book> findByReservedBy(String reservedBy);
}

