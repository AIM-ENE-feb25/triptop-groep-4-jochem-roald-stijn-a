package com.example.demo;

public class EigenBeheerHandler implements IBookingAdapter {
    private static final String[] bookings = new String[10];
    private static int bookingCount = 0;

    @Override
    public String[] getBookings() {
        return bookings;
    }

    @Override
    public void makeBooking(String booking) {
        if (bookingCount < bookings.length) {
            bookings[bookingCount++] = booking;
        } else {
            System.out.println("No more bookings can be made.");
        }
    }
}
