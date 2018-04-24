package com.exercise;

import org.junit.Test;

import static com.exercise.Direction.AIRPORT;
import static com.exercise.Direction.PLANE;
import static org.junit.Assert.*;

public class TravelTest {
    @Test
    public void create() {
        Vehicle vehicle = Vehicle.withPassengers(Person.newPilot(), Person.newOfficer());
        Travel travel = Travel.with(vehicle, AIRPORT, PLANE);
        assertNotNull(travel);
    }
}