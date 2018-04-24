package com.exercise.travel;

import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.travel.Travel;
import com.exercise.travel.TravelLog;
import org.junit.Test;

import static com.exercise.travel.Direction.AIRPORT;
import static com.exercise.travel.Direction.PLANE;
import static org.junit.Assert.*;

public class TravelLogTest {
    @Test
    public void create() {
        TravelLog log = TravelLog.of();
        assertNotNull(log);
    }

    @Test
    public void addTravel() {
        TravelLog log = TravelLog.of();
        Travel travel = buildSimpleTravel();
        log.addTravel(travel);
        assertTrue(log.hasTravels());
    }

    private Travel buildSimpleTravel() {
        return Travel.with(Vehicle.withPassengers(Person.newPilot(), Person.newOfficer()), AIRPORT, PLANE);
    }
}