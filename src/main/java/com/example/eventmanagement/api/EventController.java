
package com.example.eventmanagement.api;

import com.example.eventmanagement.datamodel.Event;
import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event.getTitle(), event.getDescription(), event.getDateTime(), event.getCapacity());
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<?> getEventAttendees(@PathVariable Long eventId) {
        List<User> attendees = eventService.getAttendeesForEvent(eventId);
        return ResponseEntity.ok(attendees);
    }
}
