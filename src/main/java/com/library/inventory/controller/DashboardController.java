package com.library.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/reader/dashboard")
    public String readerDashboard() {
        return "reader/dashboard"; // reader/dashboard.html
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // admin/dashboard.html
    }
}
