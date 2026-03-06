package com.library.inventory.service;

import com.library.inventory.model.User;
import com.library.inventory.repository.BookRepository;
import com.library.inventory.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public NotificationService(JavaMailSender mailSender,
                               BookRepository bookRepository,
                               UserRepository userRepository) {
        this.mailSender = mailSender;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Send email 24 hours before reservation ends
    public void sendExpiringReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        bookRepository.findAll().stream()
                .filter(b -> b.getReservationEnd() != null && b.getReservationEnd().equals(tomorrow))
                .forEach(book -> {
                    userRepository.findByUsername(book.getReservedBy()).ifPresent(user -> {
                        sendEmail(
                                user.getEmail(),
                                "Reminder: Book reservation expiring tomorrow",
                                "Dear " + user.getUsername() + ",\n\n" +
                                        "This is a reminder that your reservation for \"" + book.getTitle() + "\" " +
                                        "expires tomorrow (" + book.getReservationEnd() + ").\n\n" +
                                        "Please return the book on time.\n\n" +
                                        "Library Management System"
                        );
                    });
                });
    }

    // Send email on the exact end date
    public void sendExpiredReminders() {
        LocalDate today = LocalDate.now();

        bookRepository.findAll().stream()
                .filter(b -> b.getReservationEnd() != null && b.getReservationEnd().equals(today))
                .forEach(book -> {
                    userRepository.findByUsername(book.getReservedBy()).ifPresent(user -> {
                        sendEmail(
                                user.getEmail(),
                                "Today is the last day for your book reservation",
                                "Dear " + user.getUsername() + ",\n\n" +
                                        "Today is the last day of your reservation for \"" + book.getTitle() + "\".\n\n" +
                                        "Please return the book today.\n\n" +
                                        "Library Management System"
                        );
                    });
                });
    }

    // Generic method to send an email
    private void sendEmail(String to, String subject, String text) {
        if (to == null || to.isEmpty()) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("francescodetoma1@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}