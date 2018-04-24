package com.exercise;

import static com.exercise.BasicRole.*;

public class Person {
    private final BasicRole role;

    Person(BasicRole role) {
        this.role = role;
    }

    public static final Person newOfficer() {
        return new Person(OFFICER);
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
        return this.role.hasRestrictions();
    }

    public BasicRole getRole() {
        return this.role;
    }
}
