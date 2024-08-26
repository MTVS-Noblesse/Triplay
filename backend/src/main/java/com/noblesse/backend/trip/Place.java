package com.noblesse.backend.trip;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private long placeId;

    @Column(name = "place_title")
    private String placeTitle;

    @Column(name = "address")
    private String address;

    @Column(name = "openhour")
    private String openhour;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "place_order")
    private int placeOrder;

    @Column(name = "place_thumbnail")
    private String placeThumbnail;

    @ManyToOne
    @JoinColumn(name = "trip_date_id", nullable = false)
    private TripDate tripDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
