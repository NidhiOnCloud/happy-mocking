package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceVerifyTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private MeetingRoomService meetingRoomService;

    @Mock
    private BookingDAO bookingDAO;
    @Mock
    private MailSender mailSender;

    @Test
    //Verifies if the method is invoked with specified parameters.
    void should_Invoke_Payment_With_Only_400() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        //when
            bookingService.makeBooking(request);
        //then
            verify(paymentService,times(1)).pay(request,400.0);
    }
    @Test
    void should_Not_Invoke_Payment_AgainWith_Only_400() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        //when
        bookingService.makeBooking(request);
        //then
        verify(paymentService,times(1)).pay(request,400.0);
        verifyNoMoreInteractions(paymentService); // ---> should_Not_Invoke_Payment_AgainWith_Only_400
    }
    @Test
    void should_Not_Invoke_Payment_Other_Price() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,true);
        //when
        bookingService.makeBooking(request);
        //then
        verify(paymentService,never()).pay(request,200.0);
    }
    @Test
    void should_Not_Invoke_Payment_With_Any_Booking_Request_AnyPrice() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,false);
        //when
        bookingService.makeBooking(request);
        //then
        verify(paymentService,never()).pay(any(),anyDouble());
    }
}
