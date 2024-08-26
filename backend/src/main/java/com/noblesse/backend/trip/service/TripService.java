package com.noblesse.backend.trip.service;

import com.noblesse.backend.trip.domain.Place;
import com.noblesse.backend.trip.domain.Trip;
import com.noblesse.backend.trip.domain.TripDate;
import com.noblesse.backend.trip.dto.PlaceDTO;
import com.noblesse.backend.trip.dto.TripDateDTO;
import com.noblesse.backend.trip.dto.TripRegisterDTO;
import com.noblesse.backend.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    private TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Transactional
    public void registerNewTrip(TripRegisterDTO tripInfo) {
        Trip trip = new Trip();
        trip.setTripTitle(tripInfo.getTripTitle());
        trip.setTripParty(tripInfo.getTripParty());

        List<TripDate> tripDates = new ArrayList<>();
        for (TripDateDTO tripDateDTO : tripInfo.getTripDates()) {
            TripDate tripDate = new TripDate();
            tripDate.setTripStartDate(tripDateDTO.getTripStartDate());
            tripDate.setTripEndDate(tripDateDTO.getTripEndDate());
            tripDate.setTrip(trip);

            List<Place> places = new ArrayList<>();
            for (PlaceDTO placeDTO : tripDateDTO.getPlaces()) {
                Place place = new Place();
                place.setPlaceTitle(placeDTO.getPlaceTitle());
                place.setAddress(placeDTO.getAddress());
                place.setOpenHour(placeDTO.getOpenHour());
                place.setDepartureTime(placeDTO.getDepartureTime());
                place.setArrivalTime(placeDTO.getArrivalTime());
                place.setPlaceOrder(placeDTO.getPlaceOrder());
                place.setPlaceThumbnail(placeDTO.getPlaceThumbnail());
                place.setTripDate(tripDate);

                places.add(place);
            }
            tripDate.setPlaces(places);
            tripDates.add(tripDate);
        }
        trip.setTripDates(tripDates);
        tripRepository.save(trip);
    }
}
