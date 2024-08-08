package com.noblesse.backend.trip;

import jakarta.persistence.*;

@Entity
@Table(name="TBL_TRIP")
public class Trip {

    @Id
    @Column(name="TRIP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(name="TRIP_TITLE")
    private String tripTitle;

    @Column(name="TRIP_PARTY")
    private String tripParty;
}
