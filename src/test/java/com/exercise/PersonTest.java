package com.exercise;

import org.junit.Test;

import static com.exercise.Role.*;
import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void create() {
        assertNotNull(Person.newCrewMember());
    }

    @Test
    public void testHasRestrictions_false() {
        assertFalse(Person.newCrewMember().hasRestrictions());
    }

    @Test
    public void testeHasRestrictions_true() {
        Person person = Person.newCrewMember();
        Person restricted = Person.newCrewMember();
        person.addRestriction(restricted);

        assertTrue(person.hasRestrictions());
    }

    @Test
    public void createCrewMemberAndCheckItCannotDrive() {
        Person person = Person.newCrewMember();
        checkPersonWithRole(person, CREW_MEMBER);
        checkItCannotDrive(person);
    }

    @Test
    public void createPilotAndCheckHeCanDrive() {
        Person person = Person.newPilot();
        checkPersonWithRole(person, PILOT);
        checkItCanDrive(person);
    }

    @Test
    public void createFlightAttendantAndCheckSheCannotDrive() {
        Person person = Person.newFlightAttendant();
        checkPersonWithRole(person, FLIGHT_ATTENDANT);
        checkItCannotDrive(person);
    }

    @Test
    public void createOnboardChiefAndCheckHeCanDrive() {
        Person person = Person.newOnboardChief();
        checkPersonWithRole(person, ONBOARD_CHIEF);
        checkItCanDrive(person);
    }

    @Test
    public void createPolicemanAndCheckHeCanDrive() {
        Person person = Person.newPoliceman();
        checkPersonWithRole(person, POLICEMAN);
        checkItCanDrive(person);
    }

    @Test
    public void createPrisonerAndCheckHeCannotDrive() {
        Person person = Person.newPrisoner();
        checkPersonWithRole(person, PRISONER);
        checkItCannotDrive(person);
    }

    private void checkPersonWithRole(Person person, Role role) {
        assertEquals(role, person.getRole());
    }

    private void checkItCanDrive(Person person) {
        assertTrue(person.canDrive());
    }

    private void checkItCannotDrive(Person person) {
        assertFalse(person.canDrive());
    }
}