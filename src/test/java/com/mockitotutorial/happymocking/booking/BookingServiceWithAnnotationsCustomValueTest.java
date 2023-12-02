package com.mockitotutorial.happymocking.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceWithAnnotationsCustomValueTest {
    @Mock
    private  RoomService roomServiceMock;
    @InjectMocks
    private BookingService bookingService;

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        List<Room> availableRooms = roomServiceMock.getAvailableRooms();
        System.out.println(availableRooms); // mock by default return empty collection if the return type is a collection.
        //given
        when(roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1",5)));
        int expected = 5;
        //when
        int actual = bookingService.getAvailablePlaceCount();
        //then
        assertEquals(expected,actual);
    }

    @Test
    void should_CountAvailablePlaces_When_MoreThanOneRoomAvailable() {
        //given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Arrays.asList(new Room("Room 1",2), new Room("Room 2",5)));
        int expected = 7;
        //when
        int actual = bookingService.getAvailablePlaceCount();
        //then
        assertEquals(expected,actual);
    }
}
