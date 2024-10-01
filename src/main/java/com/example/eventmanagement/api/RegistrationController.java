package com.example.eventmanagement.api;

import com.example.eventmanagement.datamodel.User;
import com.example.eventmanagement.utils.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.eventmanagement.services.EventService;
import com.example.eventmanagement.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class RegistrationController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private RateLimiter rateLimiter;

    @PostMapping("/{eventId}/register")
    public ResponseEntity<?> registerForEvent(@PathVariable Long eventId, @RequestBody Map<String, String> request) {
        String email = request.get("email"); // Email of the user
        User user = userService.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        // Check rate limiting
        if (rateLimiter.isRateLimited(user.getEmail())) {
            return ResponseEntity.status(429).body("Too many requests. Please wait.");
        }

        String responseMessage = eventService.registerUserForEvent(eventId, user);
        return ResponseEntity.ok(responseMessage);
    }
}