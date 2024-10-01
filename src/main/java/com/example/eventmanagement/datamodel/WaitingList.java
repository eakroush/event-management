package com.example.eventmanagement.datamodel;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WaitingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Long userId;

    // Constructors
    public WaitingList() {}

    public WaitingList(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

}