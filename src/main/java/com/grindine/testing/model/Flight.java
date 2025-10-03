package com.grindine.testing.model;

import java.util.List;
import java.util.stream.Collectors;

public class Flight {
    private final int id;
    private final List<Segment> segments;

    public Flight(final int id, final List<Segment> segs) {
        this.id =id;
        segments = segs;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id"  + this.id + " : " +segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
