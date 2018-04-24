package com.exercise.travel;

import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import org.junit.Test;

import static com.exercise.travel.Place.AIRPORT;
import static com.exercise.travel.Place.PLANE;
import static org.junit.Assert.*;

public class TravelTest {
    @Test
    public void create() {
        Vehicle vehicle = Vehicle.withPassengers(Person.newPilot(), Person.newOfficer());
        Travel travel = Travel.with(vehicle, AIRPORT, PLANE);
        assertNotNull(travel);
    }
}