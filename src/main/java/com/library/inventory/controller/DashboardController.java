package com.library.inventory.controller;

import com.library.inventory.dto.BookDTO;
import com.library.inventory.service.BookService;
import com.library.inventory.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DashboardController {

    private final BookService bookService;
    private final UserRepository userRepository;

    public DashboardController(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @GetMapping("/reader/dashboard")
    public String readerDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("username", username);
        model.addAttribute("myReservations", bookService.getAllBooks().stream()
                .filter(b -> username.equals(b.getReservedBy()))
                .toList());
        return "reader/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<BookDTO> books = bookService.getAllBooks();
        long reservedCount = books.stream()
                .filter(b -> b.getStatus().name().equals("RESERVED"))
                .count();
        model.addAttribute("books", books);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("reservedCount", reservedCount);
        return "admin/dashboard";
    }
}
