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
    public void testHasRestrictions_false() {
        Person person = new Person();
        assertFalse(person.hasRestrictions());
    }

    @Test
    public void testeHasRestrictions_true() {
        Person person = new Person();
        Person restricted = new Person();
        person.addRestriction(restricted);

        assertTrue(person.hasRestrictions());
    }

    @Test
    public void createCrewMemberByDefaultAndCheckItCannotDrive() {
        Person person = new Person();
        assertEquals(CREW_MEMBER, person.getRole());
        checkItCannotDrive(person);
    }

    @Test
    public void createPilotAndCheckHeCanDrive() {
        Person person = buildAndCheckPersonWithRole(PILOT);
        checkItCanDrive(person);
    }

    @Test
    public void createFlightAttendantAndCheckSheCannotDrive() {
        Person person = buildAndCheckPersonWithRole(FLIGHT_ATTENDANT);
        checkItCannotDrive(person);
    }

    @Test
    public void createOnboardChiefAndCheckHeCanDrive() {
        Person person = buildAndCheckPersonWithRole(ONBOARD_CHIEF);
        checkItCanDrive(person);
    }

    @Test
    public void createPolicemanAndCheckHeCanDrive() {
        Person person = buildAndCheckPersonWithRole(Role.POLICEMAN);
        checkItCanDrive(person);
    }

    @Test
    public void createPrisonerAndCheckHeCannotDrive() {
        Person person = buildAndCheckPersonWithRole(Role.PRISONER);
        checkItCannotDrive(person);
    }

    private Person buildAndCheckPersonWithRole(Role role) {
        Person person = new Person(role);
        assertEquals(role, person.getRole());
        return person;
    }

    private void checkItCanDrive(Person person) {
        assertTrue(person.canDrive());
    }

    private void checkItCannotDrive(Person person) {
        assertFalse(person.canDrive());
    }
}