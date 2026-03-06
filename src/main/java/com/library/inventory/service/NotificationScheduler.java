package com.library.inventory.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Runs every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void checkReservations() {
        notificationService.sendExpiringReminders();
        notificationService.sendExpiredReminders();
    }
}