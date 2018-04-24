package com.exercise;

import com.google.common.collect.Lists;

import java.util.List;

import static com.exercise.Role.*;

public class Person {
    private final List<Person> restrictions = Lists.newLinkedList();
    private final Role role;

    Person(Role role) {
        this.role = role;
    }

    public static final Person newCrewMember() {
        return new Person(CREW_MEMBER);
    }

    public static final Person newPilot() {
        return new Person(PILOT);
    }

    public static final Person newPoliceman() {
        return new Person(POLICEMAN);
    }

    public static final Person newFlightAttendant() {
        return new Person(FLIGHT_ATTENDANT);
    }

    public static final Person newOnboardChief() {
        return new Person(ONBOARD_CHIEF);
    }

    public static final Person newPrisoner() {
        return new Person(PRISONER);
    }

    public boolean canDrive() {
        return this.role.canDrive();
    }

    public boolean hasRestrictions() {
        return !this.restrictions.isEmpty();
    }

    public void addRestriction(Person restricted) {
        this.restrictions.add(restricted);
    }

    public Role getRole() {
        return this.role;
    }
}
