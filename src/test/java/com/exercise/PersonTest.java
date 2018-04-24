package com.exercise;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void testCreate() {
        Person person = new Person();
        assertNotNull(person);
    }

    @Test
    public void testCanDrive_false() {
        Person person = new Person();
        assertFalse(person.canDrive());
    }

    @Test
    public void testCanDrive(){
        Person person = new Driver();
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
    public void testCreateCrewMemberByDefault() {
        Person person = new Person();
        assertEquals(Role.CREW_MEMBER, person.getRole());
    }

    @Test
    public void testCreatePilot() {
        buildAndCheckPersonWithRole(Role.PILOT);
    }

    @Test
    public void testCreateFlightAttendant() {
        buildAndCheckPersonWithRole(Role.FLIGHT_ATTENDANT);
    }

    private Person buildAndCheckPersonWithRole(Role role) {
        Person person = new Person(role);
        assertEquals(role, person.getRole());
        return person;
    }
}