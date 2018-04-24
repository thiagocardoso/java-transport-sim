package com.exercise;

import com.google.common.base.Objects;

import static com.exercise.BasicRole.*;
import static com.google.common.base.Preconditions.checkNotNull;

public class Person {
    private final BasicRole role;

    Person(BasicRole role) {
        checkNotNull(role);

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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Person) {
            Person other = (Person) o;
            return Objects.equal(this.role, other.role);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role);
    }

    @Override
    public String toString() {
        return "Person{" +
                "role=" + role +
                '}';
    }

    public boolean hasRestrictionWith(Person person) {
        return this.role.containsRestriction(person.getRole());
    }
}
