package com.poc.happymocking.booking;

import java.util.*;

public class PaymentService {
	public static final double MIN_PRICE = 200.0;
	public static final int MAX_GUESTS = 3;
	private final Map<String, Double> payments = new HashMap<>();
	public String pay(BookingRequest bookingRequest, double price) {
		if (price > MIN_PRICE && bookingRequest.getGuestCount() < MAX_GUESTS) {
			throw new UnsupportedOperationException("Only small payments are supported.");
		}
		String id = UUID.randomUUID().toString();
		payments.put(id, price);
		return id;
	}
}
