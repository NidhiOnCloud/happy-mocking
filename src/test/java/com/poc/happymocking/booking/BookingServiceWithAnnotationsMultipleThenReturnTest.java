package com.poc.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceWithAnnotationsMultipleThenReturnTest {
    @Mock
    private MeetingRoomService meetingRoomServiceMock;
    @InjectMocks
    private BookingService bookingService;
    @Test
    void should_CountAvailablePlaces_When_CalledMultipleTimes() {
        //given
        when(meetingRoomServiceMock.getAvailableRooms())
                .thenReturn(Arrays.asList(new Room("Room 1",5),new Room("Room 2",2)))
                .thenReturn(Collections.<Room>emptyList());
        int expectedFirst = 7;
        int expectedSecond = 0;
        //when
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();
        //then
            //assertEquals(expectedFirst,actualFirst);
            //assertEquals(expectedSecond,actualSecond);
        assertAll(()->assertEquals(actualFirst,expectedFirst),
                ()->assertEquals(actualSecond,expectedSecond));
    }
}
