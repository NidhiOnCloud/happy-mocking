package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class BookingServiceWithAnnotationsTest {
    @InjectMocks
    private BookingService bookingService;

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
