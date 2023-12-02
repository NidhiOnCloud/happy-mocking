package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceThrowingExceptionTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private MeetingRoomService meetingRoomService;

    @Test
    void should_ThrowException_When_NoRoom_Available() {
        //given
        BookingRequest request = new BookingRequest("1", LocalDate.of(2023,01,01),
                LocalDate.of(2023,01,05),2,false);
        when(meetingRoomService.findAvailableRoomId(request)).thenThrow(BusinessException.class);
        //when
            Executable executable = () -> bookingService.makeBooking(request);
        //then
        assertThrows(BusinessException.class,executable);
    }
}
