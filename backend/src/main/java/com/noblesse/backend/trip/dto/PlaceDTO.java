package com.noblesse.backend.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {
    private String placeTitle;
    private String address;
    private String openHour;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int placeOrder;
    private String placeThumbnail;
}
