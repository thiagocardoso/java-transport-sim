package com.exercise.entity;

import com.exercise.role.BasicRole;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import com.exercise.role.Role;
import com.google.common.collect.ComparisonChain;

import static com.exercise.role.BasicRole.*;
import static com.google.common.base.Preconditions.checkNotNull;

public class Person implements Comparable<Person> {
    private final Role role;

    Person(Role role) {
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

    public Role getRole() {
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
        return ((BasicRole)this.role).name();
    }

    public boolean hasRestrictionWith(Person person) {
        if (person != null)
            return this.role.containsRestriction(person.getRole());

        return false;
    }

    @Override
    public int compareTo(Person o) {
        return ComparisonChain.start().compare(((BasicRole)this.role).name(), ((BasicRole)o.role).name()).result();
    }
}
