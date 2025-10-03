package com.grindine.testing.filter;

import com.grindine.testing.model.Flight;
import com.grindine.testing.util.FlightUtil;

import java.time.LocalDateTime;

public class DepartedFlightFilter implements FlightFilter {
    private LocalDateTime whenDeparted;

    /**
     * checks if flight has already departed:
     * the earliest segment is before given time - return true
     */
    public DepartedFlightFilter(LocalDateTime whenDeparted) {
        this.whenDeparted = whenDeparted;
    }

    @Override
    public boolean test(Flight flight) {
        FlightUtil.orderFlightSegments(flight);
        return flight.getSegments().get(0).getDepartureDate().isAfter(whenDeparted);

    }
}
