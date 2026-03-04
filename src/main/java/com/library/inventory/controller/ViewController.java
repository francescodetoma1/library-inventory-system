package com.library.inventory.controller;

import com.library.inventory.service.UserService;
import com.library.inventory.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    private final BookService bookService;
    private final UserService userService;

    public ViewController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    // BOOK PAGE - Shows the list of available books
    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books"; // This points to templates/books.html
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

}