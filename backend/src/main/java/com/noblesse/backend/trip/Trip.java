package com.noblesse.backend.trip;

import jakarta.persistence.*;

@Entity
@Table(name="TBL_TRIP")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TRIP_ID")
    private Long tripId;

    @Column(name="TRIP_TITLE")
    private String tripTitle;

    @Column(name="TRIP_PARTY")
    private String tripParty;
}
