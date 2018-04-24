package com.exercise.business;

import com.exercise.entity.Person;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.exercise.travel.Place.AIRPORT;
import static org.junit.Assert.*;

public class GuideTest {
    @Test
    public void create() {
        assertNotNull(buildSimpleGuide());
    }

    @Test(expected = Guide.DriverNotFoundException.class)
    public void nextDriver_driverNotFoundException() {
        Guide guide = buildSimpleGuide();
        guide.nextDriver();
    }

    @Test
    public void nextDriver() {
        List<Person> passengers = Lists.newArrayList(Person.newPilot());
        Guide guide = buildGuide(passengers);
        assertNotNull(guide.nextDriver());
    }

    @Test(expected = Guide.PassengerNotFoundForDriverException.class)
    public void nextPassenger_passengerNotFound() {
        List<Person> passengers = Lists.newArrayList(Person.newPilot());
        Guide guide = buildGuide(passengers);
        Person driver = guide.nextDriver();
        assertNotNull(guide.nextPassengerByDriver(driver));
    }

    @Test
    public void nextPassenger() {
        List<Person> passengers = Lists.newArrayList(Person.newPilot(), Person.newOfficer());
        Guide guide = buildGuide(passengers);
        Person driver = guide.nextDriver();
        assertNotNull(guide.nextPassengerByDriver(driver));
    }

    @Test
    public void nextTravel_valid() {
        List<Person> passengers = Lists.newArrayList(Person.newPilot(), Person.newOfficer());

        Guide guide = buildGuide(passengers);
        Travel next = guide.nextTravel();

        assertNotNull(next);
        assertTrue(next.getVehicle().canTravel());
    }

    @Test
    public void nextTravel_withoutPassenger() {
        List<Person> passengers = Lists.newArrayList(Person.newPilot());

        Guide guide = buildGuide(passengers);
        Travel next = guide.nextTravel();

        assertNotNull(next);
        assertTrue(next.getVehicle().canTravel());
    }

    private Guide buildSimpleGuide() {
        return buildGuide(Lists.newLinkedList());
    }

    private Guide buildGuide(List<Person> passengers) {
        Place currentPosition = AIRPORT;
        List<Person> availablePassengers = passengers;
        return Guide.of(currentPosition, availablePassengers);
    }

}