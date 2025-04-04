package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class BookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}
}
