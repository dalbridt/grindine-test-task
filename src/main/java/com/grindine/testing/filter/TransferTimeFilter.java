package com.grindine.testing.filter;

import com.grindine.testing.model.Flight;
import com.grindine.testing.model.Segment;
import com.grindine.testing.util.FlightUtil;

import java.time.Duration;
import java.time.LocalDateTime;

public class TransferTimeFilter implements FlightFilter {
    private Duration maxDuration;

    public TransferTimeFilter(int hoursFilterThreshold) {
        this.maxDuration = Duration.ofHours(hoursFilterThreshold);
    }

    public boolean test(Flight flight) {
        FlightUtil.orderFlightSegments(flight);
        Duration transferTime = Duration.ZERO;
        LocalDateTime ariv1 = null;
        for (Segment segment : flight.getSegments()) {
            if (ariv1 != null) {
                transferTime = transferTime.plus(Duration.between(ariv1, segment.getDepartureDate()));
            }
            ariv1 = segment.getArrivalDate();
        }
        return transferTime.compareTo(maxDuration) < 0;
    }
}
