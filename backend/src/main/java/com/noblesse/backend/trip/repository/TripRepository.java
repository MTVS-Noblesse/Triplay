package com.noblesse.backend.trip.repository;

import com.noblesse.backend.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> { }
