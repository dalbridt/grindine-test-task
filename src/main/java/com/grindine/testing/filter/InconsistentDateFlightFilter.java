package com.grindine.testing.filter;

import com.grindine.testing.model.Flight;


/**
 * checks is there's no segments with departure time later than arrival time
 */
public class InconsistentDateFlightFilter implements FlightFilter {
    @Override
    public boolean test(Flight flight) {
        return flight.getSegments().stream()
                .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()));
    }
}
