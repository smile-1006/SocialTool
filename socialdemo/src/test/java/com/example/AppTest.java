package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void testIsWithinLastSixMonths() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Date within the last six months
        String dateWithinSixMonths = now.minusMonths(3).format(formatter);
        assertTrue(App.isWithinLastSixMonths(dateWithinSixMonths));
        
        // Date more than six months ago
        String dateMoreThanSixMonthsAgo = now.minusMonths(7).format(formatter);
        assertFalse(App.isWithinLastSixMonths(dateMoreThanSixMonthsAgo));
    }
}
