package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {

    private final IBookingAdapter[] adapters = {
        new BookingComAdapter(),
        new TripAdvisorAdapter()
    };

    public Hotel[] searchHotels(String checkInDate, String checkOutDate) {
        List<Hotel> allHotels = new ArrayList<>();
        for (IBookingAdapter adapter : adapters) {
            Hotel[] AdapterHotels = adapter.searchHotels(checkInDate, checkOutDate);
            if (AdapterHotels != null) {
                allHotels.addAll(Arrays.asList(AdapterHotels));
            }
        }
        return allHotels.toArray(new Hotel[0]);
    }
}
