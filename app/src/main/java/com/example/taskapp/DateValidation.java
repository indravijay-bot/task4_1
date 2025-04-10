package com.example.taskapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {

    // This method checks if a date string is valid according to the "dd/MM/yyyy" format.
    public static boolean isDateValid(String date) {
        // Return false if the input date string is null or empty
        if (date == null || date.isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Disables lenient parsing to strictly validate the date format

        try {
            // Parse the input date string into a Date object
            Date parsedDate = sdf.parse(date);

            // Get today's date for comparison
            Date today = new Date();

            // Ensure the given date is not the same as today's date
            if (sdf.format(today).equals(sdf.format(parsedDate))) {
                return false; // The date should not match today's date
            }

            // Check if the date is in the past
            if (parsedDate.before(today)) {
                return false; // The date should not be in the past
            }
        } catch (ParseException e) {
            // Handle any parsing exceptions
            e.printStackTrace();
            return false;
        }

        return true; // The date is valid
    }

    // This method checks whether the title, description, and due date are valid.
    public static boolean isInputValid(String title, String description, String dueDate) {
        // Return false if any of the required fields are null or empty
        return !(title == null || title.isEmpty() || description == null || description.isEmpty() || dueDate == null || dueDate.isEmpty());
    }
}
