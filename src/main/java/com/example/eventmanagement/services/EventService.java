
package com.example.eventmanagement.services;

import com.example.eventmanagement.database.*;
import com.example.eventmanagement.datamodel.Event;
import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.datamodel.WaitingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WaitingListRepository waitingListRepository;

    public Event createEvent(String title, String description, LocalDateTime dateTime, int capacity) {
        Event event = new Event(title, description, dateTime, capacity);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public String registerUserForEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return "Event not found!";
        }

        if (event.isClosed()) {
            return "Event is closed.";
        }

        if (event.getCapacity() > 0) {
            // Register user and decrease capacity
            event.setCapacity(event.getCapacity() - 1);
            eventRepository.save(event);
            return "User successfully registered for the event.";
        } else {
            // Add user to the waiting list
            WaitingList waitingListEntry = new WaitingList(eventId, user.getId());
            waitingListRepository.save(waitingListEntry);
            return "Event is full. You have been added to the waiting list.";
        }
    }

    // For ADMIN use
    public List<User> getAttendeesForEvent(Long eventId) {
        // Fetch attendees for the event using your repository (you may need a join query)
        return eventRepository.findAttendeesByEventId(eventId); // Placeholder for attendee fetching logic
    }

    // System Stats for ADMIN use
    public int getTotalEvents() {
        return (int) eventRepository.count();
    }
}