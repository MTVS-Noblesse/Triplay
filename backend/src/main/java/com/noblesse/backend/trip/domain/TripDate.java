package com.noblesse.backend.trip.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_trip_date")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_date_id")
    private long tripDateId;

    @Column(name = "trip_start_date")
    private LocalDate tripStartDate;

    @Column(name = "trip_end_date")
    private LocalDate tripEndDate;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @OneToMany(mappedBy = "tripDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();
}
