package com.exercise;

import org.junit.Test;

import static com.exercise.Role.*;
import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void create() {
        Person person = new Person();
        assertNotNull(person);
    }

    @Test
    public void canDrive_false() {
        Person person = new Person();
        assertFalse(person.canDrive());
    }

    @Test
    public void canDrive(){
        Person person = new Person(PILOT);
        assertTrue(person.canDrive());
    }

    @Test
    public void testHasRestrictions_false() {
        Person person = new Person();
        assertFalse(person.hasRestrictions());
    }

    @Test
    public void testHasRestrictions_true() {
        Person person = new Person();
        Person restricted = new Person();
        person.addRestriction(restricted);

        assertTrue(person.hasRestrictions());
    }

    @Test
    public void createCrewMemberByDefault() {
        Person person = new Person();
        assertEquals(CREW_MEMBER, person.getRole());
    }

    @Test
    public void createPilot() {
        buildAndCheckPersonWithRole(PILOT);
    }

    @Test
    public void createFlightAttendant() {
        buildAndCheckPersonWithRole(FLIGHT_ATTENDANT);
    }

    @Test
    public void createOnboardChief() {
        buildAndCheckPersonWithRole(ONBOARD_CHIEF);
    }

    private Person buildAndCheckPersonWithRole(Role role) {
        Person person = new Person(role);
        assertEquals(role, person.getRole());
        return person;
    }
}