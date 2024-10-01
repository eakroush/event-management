
package com.example.eventmanagement.datamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dateTime;
    private int capacity;
    private boolean isClosed;

    @ManyToMany
    @JoinTable(
            name = "event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> attendees;

    public Event() {
    }

    public Event(String title, String description, LocalDateTime dateTime, int capacity) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.isClosed = false; // Set default to false when creating a new event
    }

}
