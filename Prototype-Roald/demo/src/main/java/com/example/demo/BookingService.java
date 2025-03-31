package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    public String[] getBookings() {
        String[] bookings = getBookingsFromAdapters();
        return bookings;
    }

    public void makeBooking(String booking, String destination) {
        if (destination.equals("BookingCom")) {
            BookingComAdapter bookingComAdapter = new BookingComAdapter();
            bookingComAdapter.makeBooking(booking);
        } else if (destination.equals("TripAdvisor")) {
            TripAdvisorAdapter tripAdvisorAdapter = new TripAdvisorAdapter();
            tripAdvisorAdapter.makeBooking(booking);            
        } else if (destination.equals("EigenBeheer")) {
            EigenBeheerHandler eigenBeheerHandler = new EigenBeheerHandler();
            eigenBeheerHandler.makeBooking(booking);
        } else {
            System.out.println("Invalid destination");
        }
    }

    private String[] getBookingsFromAdapters() {
        List<String> allBookings = new ArrayList<>();

        BookingComAdapter bookingComAdapter = new BookingComAdapter();
        String[] bookingComBookings = bookingComAdapter.getBookings();
        for (String booking : bookingComBookings) {
            if (booking != null) {
                allBookings.add(booking);
            }
        }

        TripAdvisorAdapter tripAdvisorAdapter = new TripAdvisorAdapter();
        String[] tripAdvisorBookings = tripAdvisorAdapter.getBookings();
        for (String booking : tripAdvisorBookings) {
            if (booking != null) {
                allBookings.add(booking);
            }
        }

        EigenBeheerHandler eigenBeheerHandler = new EigenBeheerHandler();
        String[] eigenBeheerBookings = eigenBeheerHandler.getBookings();
        for (String booking : eigenBeheerBookings) {
            if (booking != null) {
                allBookings.add(booking);
            }
        }

        return allBookings.toArray(new String[0]);
    }
}
