package com.mockitotutorial.happymocking.booking;

import java.time.temporal.ChronoUnit;
public class BookingService {
	private final PaymentService paymentService;
	private final MeetingRoomService meetingRoomService;
	private final BookingDAO bookingDAO;
	private final MailSender mailSender;
	private final static double BASE_PRICE_USD = 50.0;
	public int getAvailablePlaceCount() {
		return meetingRoomService.getAvailableRooms()
				.stream()
				.map(room -> room.getCapacity())
				.reduce(0, Integer::sum);
	}
	public double calculatePrice(BookingRequest bookingRequest) {
		long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
		return nights > 0 ? BASE_PRICE_USD * bookingRequest.getGuestCount() * nights : 0.0;
	}
	public double calculatePriceEuro(BookingRequest bookingRequest) {
		long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
		return CurrencyConverter.toEuro(BASE_PRICE_USD * bookingRequest.getGuestCount() * nights);
	}
	public String makeBooking(BookingRequest bookingRequest) {
		String roomId = meetingRoomService.findAvailableRoomId(bookingRequest);
		double price = calculatePrice(bookingRequest);
		if (bookingRequest.isPrepaid()) {
			paymentService.pay(bookingRequest, price);
		}
		bookingRequest.setRoomId(roomId);
		String bookingId = bookingDAO.save(bookingRequest);
		meetingRoomService.bookRoom(roomId);
		mailSender.sendBookingConfirmation(bookingId);
		return bookingId;
	}
	public void cancelBooking(String id) {
		BookingRequest request = bookingDAO.get(id);
		meetingRoomService.unBookMeetingRoom(request.getRoomId());
		bookingDAO.delete(id);
	}
	public BookingService(PaymentService paymentService, MeetingRoomService meetingRoomService, BookingDAO bookingDAO,
						  MailSender mailSender) {
		super();
		this.paymentService = paymentService;
		this.meetingRoomService = meetingRoomService;
		this.bookingDAO = bookingDAO;
		this.mailSender = mailSender;
	}
}
