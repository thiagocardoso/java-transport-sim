package com.exercise;

import com.google.common.collect.Lists;

import java.util.List;

public class Person {
    private List<Person> restrictions = Lists.newLinkedList();
    private Role role;

    public Person() {
        this(Role.CREW_MEMBER);
    }

    public Person(Role role) {
        this.role = role;
    }

    public boolean canDrive() {
        return false;
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
