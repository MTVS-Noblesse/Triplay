package com.noblesse.backend.trip;

import com.noblesse.backend.trip.dto.PlaceDTO;
import com.noblesse.backend.trip.dto.TripDateDTO;
import com.noblesse.backend.trip.dto.TripRegisterDTO;
import com.noblesse.backend.trip.service.TripService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class TripRegisterTest {

    @Autowired
    private TripService tripService;

    private static Stream<Arguments> newTrip() {
        return Stream.of(
                Arguments.of(new TripRegisterDTO(
                        "여름 휴가", "가족",
                        List.of(
                                new TripDateDTO(LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 12),
                                        List.of(
                                                new PlaceDTO("Beach", "123 Ocean Drive", "09:00 - 18:00", LocalTime.of(10, 0), LocalTime.of(12, 0), 1, "1.jpg")
                                        )),
                                new TripDateDTO(LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 15),
                                        List.of(
                                                new PlaceDTO("Mountain", "456 Hill Street", "08:00 - 16:00", LocalTime.of(9, 0), LocalTime.of(11, 0), 2, "2.jpg")
                                        ))
                        ))
                ),
                Arguments.of(new TripRegisterDTO(
                        "Winter Adventure", "Friends",
                        List.of(
                                new TripDateDTO(LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 25),
                                        List.of(
                                                new PlaceDTO("Ski Resort", "789 Snowy Path", "08:00 - 20:00", LocalTime.of(8, 0), LocalTime.of(18, 0), 1, "1.jpg"),
                                                new PlaceDTO("Hot Springs", "101 Warm Way", "10:00 - 22:00", LocalTime.of(12, 0), LocalTime.of(15, 0), 2, "2.jpg")
                                        ))
                        ))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("newTrip")
    void testRegisterNewTrip(TripRegisterDTO tripInfo){
            Assertions.assertDoesNotThrow(() -> {
                tripService.registerNewTrip(tripInfo);
            }
        );
    }

}
