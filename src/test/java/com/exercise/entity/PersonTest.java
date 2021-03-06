package com.exercise.entity;

import com.exercise.entity.Person;
import com.exercise.role.BasicRole;
import org.junit.Test;

import static com.exercise.role.BasicRole.*;
import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void create() {
        assertNotNull(Person.newOfficer());
    }

    @Test
    public void testeHasRestrictions_true() {
        Person person = Person.newOfficer();

        assertTrue(person.hasRestrictions());
    }

    @Test
    public void createCrewMemberAndCheckItCannotDrive() {
        Person person = Person.newOfficer();
        checkPersonWithRole(person, OFFICER);
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

    @Test
    public void hasRestrictionWith_true() {
        Person pilot = Person.newPilot();
        Person attendant = Person.newFlightAttendant();
        assertTrue(pilot.hasRestrictionWith(attendant));
    }

    @Test
    public void hasRestrictionWith_false() {
        Person pilot = Person.newPilot();
        Person officer = Person.newOfficer();
        assertFalse(pilot.hasRestrictionWith(officer));
    }

    private void checkPersonWithRole(Person person, BasicRole role) {
        assertEquals(role, person.getRole());
    }

    private void checkItCanDrive(Person person) {
        assertTrue(person.canDrive());
    }

    private void checkItCannotDrive(Person person) {
        assertFalse(person.canDrive());
    }
}