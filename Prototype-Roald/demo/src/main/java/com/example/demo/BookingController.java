package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookingController {

    @RequestMapping("/getBookings")
    public String[] getBookings() {
        String[] bookings = BookingService.getBookings();
        return bookings;
    }

    @RequestMapping("/makeBooking")
    public String makeBooking(String booking) {
        BookingService.makeBooking(booking);
        return "Booking made: " + booking;
    }
}
