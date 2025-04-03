package com.example.demo;

public interface IBookingAdapter {
    Hotel[] searchHotels(String location, String checkInDate, String checkOutDate);
}
