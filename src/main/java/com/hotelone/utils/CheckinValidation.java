package com.hotelone.utils;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class CheckinValidation {

    private final DatePicker datePicker;

    public CheckinValidation(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public DatePicker isValid() {
        if(this.datePicker.getValue() == null) return null;
        if(this.datePicker.getValue().isBefore(LocalDate.now())) return null;
        return datePicker;
    }
}
