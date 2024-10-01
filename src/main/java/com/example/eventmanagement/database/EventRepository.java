
package com.example.eventmanagement.database;

import com.example.eventmanagement.datamodel.Event;
import com.example.eventmanagement.datamodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e.attendees FROM Event e WHERE e.id = :eventId")
    List<User> findAttendeesByEventId(@Param("eventId") Long eventId);

    List<Event> findByIsClosedFalseAndDateTimeBefore(LocalDateTime dateTime);
}
