package com.example.eventmanagement.database;

import com.example.eventmanagement.datamodel.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {
    List<WaitingList> findByEventId(Long eventId);
}