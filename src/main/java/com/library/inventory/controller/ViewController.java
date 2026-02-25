package com.library.inventory.controller;

import com.library.inventory.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final BookService bookService;

    public ViewController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/books";
    }
}
