package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/hotels/search")
    public ResponseEntity<Hotel[]> searchHotels(@RequestParam String location, @RequestParam String checkInDate, @RequestParam String checkOutDate) {
        Hotel[] hotels = bookingService.searchHotels(location, checkInDate, checkOutDate);
        if (hotels.length > 0) {
            return ResponseEntity.ok(hotels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
