package com.noblesse.backend.trip;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name="TBL_TRIP_DATE")
public class TripDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TRIP_DATE_ID")
    private long tripDateId;

    @Column(name="TRIP_START_DATE")
    private LocalDate tripStartDate;

    @Column(name="TRIP_END_DATE")
    private LocalDate tripEndDate;
}
