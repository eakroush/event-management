package com.example.eventmanagement.services;

import com.example.eventmanagement.database.*;
import com.example.eventmanagement.datamodel.Event;
import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.datamodel.WaitingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private WaitingListRepository waitingListRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event("Test Event", "Event Description", LocalDateTime.now().plusDays(1), 10);
        event.setId(1L);
        user = new User();
        user.setId(1L);
        user.setEmail("user1@example.com");
    }

    @Test
    public void testRegisterUserForEvent_WithCapacity() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        String result = eventService.registerUserForEvent(1L, user);

        assertEquals("User successfully registered for the event.", result);
        assertEquals(9, event.getCapacity());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testRegisterUserForEvent_NoCapacity() {
        event.setCapacity(0);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        String result = eventService.registerUserForEvent(1L, user);

        assertEquals("Event is full. You have been added to the waiting list.", result);
        verify(waitingListRepository, times(1)).save(any(WaitingList.class));
    }
}