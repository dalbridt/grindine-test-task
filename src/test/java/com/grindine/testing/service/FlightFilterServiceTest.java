package com.grindine.testing.service;


import com.grindine.testing.filter.DepartedFlightFilter;
import com.grindine.testing.filter.FlightFilter;
import com.grindine.testing.filter.InconsistentDateFlightFilter;
import com.grindine.testing.filter.TransferTimeFilter;
import com.grindine.testing.model.Flight;
import com.grindine.testing.repository.FlightRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterServiceTest {
    LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    LocalDateTime twoDaysBeforeNow = LocalDateTime.now().minusDays(2);
    LocalDateTime now = LocalDateTime.now();

    @Test
    public void filter_departed_flights() {
        // создаем тестовый набор из трех перелетов, где один вылетел в прошлом
        FlightRepository repoDepartedFlights = new FlightRepository() {
            @Override
            public List<Flight> getFlights() {
                return Arrays.asList(
                        //A normal flight
                        createFlight(1, threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                        // already Departed fight
                        createFlight(2, twoDaysBeforeNow, twoDaysBeforeNow.plusHours(3)),
                        //A normal multi segment flight
                        createFlight(3, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
                );
            }
        };
        FlightFilterService flightFilterService = new FlightFilterService(repoDepartedFlights);
        FlightFilter depFilter = new DepartedFlightFilter(now);
        List<Flight> filtred = flightFilterService.doFilter(depFilter);

        assertEquals(2, filtred.size());
        assertEquals(1, filtred.get(0).getId());
        assertEquals(3, filtred.get(1).getId());

    }

    @Test
    public void filter_inconsistent_flights() {
        // создаем тестовый набор из трех перелетов, где два имеют сегменты с невалидным временем (прилет раньше вылета)
        FlightRepository repoInconsistentFlights = new FlightRepository() {
            @Override
            public List<Flight> getFlights() {
                return Arrays.asList(
                        //two-segment flight with inconsistent segment 2
                        createFlight(1, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                                threeDaysFromNow, threeDaysFromNow.minusHours(2)),
                        // inconsistent segment
                        createFlight(2, twoDaysBeforeNow, twoDaysBeforeNow.minusDays(1)),
                        //A normal multi segment flight
                        createFlight(3, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
                );
            }
        };
        FlightFilterService flightFilterService = new FlightFilterService(repoInconsistentFlights);

        FlightFilter inconsistencyFilter = new InconsistentDateFlightFilter();
        List<Flight> filtred = flightFilterService.doFilter(inconsistencyFilter);

        assertEquals(1, filtred.size());
        assertEquals(3, filtred.get(0).getId());

    }

    @Test
    public void should_filter_by_total_transfer_time() {
        // создаем тестовый набор из трех перелетов с разным временем на земле
        FlightRepository repoInconsistentFlights = new FlightRepository() {
            @Override
            public List<Flight> getFlights() {
                return Arrays.asList(
                        // 1 hour of transfer
                        createFlight(1, threeDaysFromNow.plusHours(1), threeDaysFromNow.plusHours(2),
                                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                        // 0 hours of transfer
                        createFlight(2, twoDaysBeforeNow.plusHours(1), twoDaysBeforeNow.plusHours(5)),
                        //6 hours of transfer
                        createFlight(3, threeDaysFromNow, threeDaysFromNow.plusHours(2),
                                threeDaysFromNow.plusHours(8), threeDaysFromNow.plusHours(10))
                );
            }
        };

        FlightFilterService flightFilterService = new FlightFilterService(repoInconsistentFlights);
        FlightFilter transferTimeFilter = new TransferTimeFilter(3);

        List<Flight> filtred = flightFilterService.doFilter(transferTimeFilter);

        assertEquals(2, filtred.size());
        assertEquals(1, filtred.get(0).getId());
        assertEquals(2, filtred.get(1).getId());
    }

    @Test
    public void all_three_filters() {
        // создаем репо
        FlightRepository repo = new TestDataClass();

        FlightFilterService flightFilterService = new FlightFilterService(repo);

        FlightFilter depFilter = new DepartedFlightFilter(now);
        FlightFilter inconsistencyFilter = new InconsistentDateFlightFilter();
        FlightFilter transferTimeFilter = new TransferTimeFilter(8);


        List<Flight> filtred = flightFilterService.doFilter(depFilter, inconsistencyFilter, transferTimeFilter);

        assertEquals(3, filtred.size());
        assertEquals(4, filtred.get(0).getId());
        assertEquals(6, filtred.get(1).getId());
        assertEquals(8, filtred.get(2).getId());
    }


}