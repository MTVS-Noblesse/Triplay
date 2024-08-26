package com.noblesse.backend.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripRegisterDTO {
    private String tripTitle;
    private String tripParty;
    private List<TripDateDTO> tripDates;

}
