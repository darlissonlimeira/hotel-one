package com.hotelone.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomCurrency {
    private static final Locale BRASIL = new Locale("pt", "BR");
    private static final NumberFormat REAL_FORMAT = NumberFormat.getCurrencyInstance(BRASIL);

    public static String format(double valor){
        return REAL_FORMAT.format(valor);
    }
}
