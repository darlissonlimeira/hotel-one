package com.hotelone.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Hospedagem {

    public static long qtdDias (LocalDate checkin, LocalDate checkout) {
        LocalDateTime dateTimeCheckin = checkin.atTime(0,0);
        LocalDateTime dateTimeCheckout = checkout.atTime(0, 0);
        return Duration.between(dateTimeCheckin, dateTimeCheckout).toDays();
    }
}
