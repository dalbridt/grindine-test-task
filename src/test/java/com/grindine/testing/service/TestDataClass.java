package com.grindine.testing.service;

import com.grindine.testing.model.Flight;
import com.grindine.testing.repository.FlightRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestDataClass implements FlightRepository {
    protected LocalDateTime now = LocalDateTime.now();
    @Override
    public List<Flight> getFlights() {
        return Arrays.asList(
                // 1. Два сегмента, во втором не согласовано время
                createFlight(1, now.plusDays(1), now.plusDays(1).plusHours(2),
                        now.plusDays(1).plusHours(3), now.plusDays(1).plusHours(2)),

                // 2. Один сегмент, уже улетел
                createFlight(2,now.minusHours(5), now.minusHours(2)),

                // 3. Общее время пересадок 10 часов
                createFlight(3,now.plusDays(1), now.plusDays(1).plusHours(2),
                        now.plusDays(1).plusHours(12), now.plusDays(1).plusHours(14)),

                // 4. Нормальный перелет
                createFlight(4,now.plusDays(2), now.plusDays(2).plusHours(3)),

                // 5. Прошедшие даты и несогласованное время
                createFlight(5, now.minusDays(2), now.minusDays(2).plusHours(2),
                        now.minusDays(2).plusHours(5), now.minusDays(2).plusHours(4)),

                // 6. Нормальный перелет
                createFlight(6, now.plusDays(3), now.plusDays(3).plusHours(4)),

                // 7. Пересадка 9 часов и уже улетел
                createFlight(7, now.minusDays(1), now.minusDays(1).plusHours(2),
                        now.minusDays(1).plusHours(11), now.minusDays(1).plusHours(13)),

                // 8. Нормальный перелет
                createFlight(8,now.plusDays(4), now.plusDays(4).plusHours(2))
        );
    }
}
