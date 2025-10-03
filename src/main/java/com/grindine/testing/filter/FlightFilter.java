package com.grindine.testing.filter;

import com.grindine.testing.model.Flight;

import java.util.Objects;
import java.util.function.Predicate;

public interface FlightFilter extends Predicate<Flight> {

    default FlightFilter and(FlightFilter other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }
}
