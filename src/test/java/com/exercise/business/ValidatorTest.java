package com.exercise.business;

import com.exercise.entity.Person;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ValidatorTest {
    @Test
    public void failValidateNotAloneWithRestriction_pilotAndAttendant() {
        List<Person> people = Lists.newArrayList(Person.newPilot(), Person.newFlightAttendant());
        Validator validator = new NotAloneWithRestrictionValidator(people);
        assertFalse(validator.validate());
    }

    @Test
    public void validateNotAloneWithRestriction_pilotAndOfficer() {
        List<Person> people = Lists.newArrayList(Person.newPilot(), Person.newOfficer());
        Validator validator = new NotAloneWithRestrictionValidator(people);
        assertTrue(validator.validate());
    }

    @Test
    public void validatePrisonerWithoutPoliceman_success() {
        List<Person> people = Lists.newArrayList(Person.newPoliceman(), Person.newPrisoner());
        Validator validator = new PrisonerWithoutPolicemanValidator(people);
        assertTrue(validator.validate());
    }

    @Test
    public void validatePrisonerWithoutPoliceman_fail() {
        List<Person> people = Lists.newArrayList(Person.newPilot(), Person.newPrisoner());
        Validator validator = new PrisonerWithoutPolicemanValidator(people);
        assertFalse(validator.validate());
    }

}