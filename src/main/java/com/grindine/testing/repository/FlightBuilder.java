package com.grindine.testing.repository;

import com.grindine.testing.model.Flight;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * the class given by the test task
 */
public class FlightBuilder implements FlightRepository {
    public List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
            //A normal flight with two hour duration
            createFlight(1, threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            //A normal multi segment flight
            createFlight(2, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
            //A flight departing in the past
            createFlight(3, threeDaysFromNow.minusDays(6), threeDaysFromNow),
            //A flight that departs before it arrives
            createFlight(4, threeDaysFromNow, threeDaysFromNow.minusHours(6)),
            //A flight with more than two hours ground time
            createFlight(5, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
            //Another flight with more than two hours ground time
            createFlight(6, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }
    @Override
    public List<Flight> getFlights() {
        return createFlights();
    }
}
