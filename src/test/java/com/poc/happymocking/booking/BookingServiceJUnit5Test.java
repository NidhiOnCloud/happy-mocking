package com.poc.happymocking.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BookingServiceJUnit5Test {
    private  PaymentService paymentServiceMock;
    private MeetingRoomService meetingRoomServiceMock;
    private  BookingDAO bookingDAOMock;
    private  MailSender mailSenderMock;
    private BookingService bookingService;
    @BeforeEach
    void setUp(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.meetingRoomServiceMock = mock(MeetingRoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, meetingRoomServiceMock,bookingDAOMock,mailSenderMock);
    }
    @Test
    void calculatePriceTest() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,false);
        double expected = 4 * 2 * 50.0;
        //when
            double actual = bookingService.calculatePrice(request);
        //then
        assertEquals(actual,expected);
    }
    @Test
    void calculatePriceFromDateLessThanToDateTest() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,05),
                LocalDate.of(2023,01,01),2,false);
        double expected = 0.0;
        //when
        double actual = bookingService.calculatePrice(request);
        //then
        assertEquals(actual,expected);
    }


}
