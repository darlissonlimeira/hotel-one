package com.hotelone.utils;

import javafx.scene.control.DatePicker;

public class CheckoutValidation {
    private final DatePicker datePicker;

    public CheckoutValidation(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public DatePicker isValid() {
        if(datePicker.getValue() == null) return null;
        if(datePicker.getValue().isAfter(datePicker.getValue())) return null;
        return datePicker;
    }
}
