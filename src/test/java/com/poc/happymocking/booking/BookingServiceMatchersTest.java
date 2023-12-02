package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceMatchersTest {
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private MeetingRoomService meetingRoomService;
    @Mock
    private PaymentService paymentService;

    @Mock
    private BookingDAO bookingDAO;
    @Mock
    private MailSender mailSender;

    @Test
    void should_NotComplete_Booking_When_PriceTooHigh() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        when(meetingRoomService.findAvailableRoomId(any())).thenReturn("ROOM_1520");
        when(paymentService.pay(any(),anyDouble())).thenThrow(BusinessException.class);
        //when
            Executable executable = () -> bookingService.makeBooking(request);
        //then
        assertThrows(BusinessException.class,executable);
    }
    @Test
    void should_Complete_Payment_When_PriceIsLess() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        when(meetingRoomService.findAvailableRoomId(any())).thenReturn("ROOM_1520");
        // if we need a combination of matcher with primitives, always use eq.
        //when(paymentService.pay(any(),eq(400.0))).thenReturn("payment received");
        when(paymentService.pay(any(),anyDouble())).thenReturn("payment received");
        //when
        String actual = bookingService.makeBooking(request);
        //then
        assertNull(actual);
    }
    @Test
    void should_Save_MeetingReq_After_payment() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        when(meetingRoomService.findAvailableRoomId(any())).thenReturn("ROOM_1520");
        // if we need a combination of matcher with primitives, always use eq.
        //when(paymentService.pay(any(),eq(400.0))).thenReturn("payment received");
        when(paymentService.pay(any(),anyDouble())).thenReturn("payment received");
        when(bookingDAO.save(request)).thenReturn("BOOKING_ID_123");
        //when
        String actual = bookingService.makeBooking(request);
        //then
        assertEquals("BOOKING_ID_123",actual);
    }

    @Test
    void should_Send_Email_After_Saving_Req() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        when(meetingRoomService.findAvailableRoomId(any())).thenReturn("ROOM_1520");
        // if we need a combination of matcher with primitives, always use eq.
        //when(paymentService.pay(any(),eq(400.0))).thenReturn("payment received");
        when(paymentService.pay(any(),anyDouble())).thenReturn("payment received");
        when(bookingDAO.save(request)).thenReturn("BOOKING_ID_123");
        doNothing().when(mailSender).sendBookingConfirmation("BOOKING_ID_123");
        //when
        String actual = bookingService.makeBooking(request);
        //then
        assertEquals("BOOKING_ID_123",actual);
    }
}
