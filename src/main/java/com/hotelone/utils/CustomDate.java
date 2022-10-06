package com.hotelone.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class CustomDate {
    private static final String PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

    public static String format(LocalDate date) {
        return formatter.format(date);
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, formatter);
    }
}
