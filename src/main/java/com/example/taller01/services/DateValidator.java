package com.example.taller01.services;

import java.time.LocalDate;

public class DateValidator {
    public static boolean canAccessSystem(String hiringDateString) {
        // Parse the input date string into a LocalDate object
        LocalDate hiringDate = LocalDate.parse(hiringDateString);

        // Calculate today's date
        LocalDate today = LocalDate.now();

        // Calculate the difference between the hiring date and today's date
        long daysSinceHiringDate = hiringDate.until(today).getDays();

        // Check if the employee can access the system based on the number of days since hiring
        return daysSinceHiringDate >= 0;
    }
}
