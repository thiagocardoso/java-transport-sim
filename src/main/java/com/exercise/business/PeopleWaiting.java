package com.exercise.business;

import com.exercise.entity.Person;
import com.exercise.travel.Place;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class PeopleWaiting {
    private final Place location;
    private final List<Person> people;

    PeopleWaiting(Place location) {
        this.location = location;
        this.people = Lists.newLinkedList();
    }

    public static final PeopleWaiting of(Place location) {
        return new PeopleWaiting(location);
    }

    public static final List<PeopleWaiting> forAllLocations() {
        List<PeopleWaiting> people = Lists.newLinkedList();

        for (Place place : Place.values())
            people.add(PeopleWaiting.of(place));

        return people;
    }

    public Place getLocation() {
        return this.location;
    }

    public List<Person> getPeople() {
        Collections.sort(this.people);
        return ImmutableList.copyOf(this.people);
    }

    public void addPeople(List<Person> people) {
        if(people!=null && !people.isEmpty())
            this.people.addAll(people);

        Collections.sort(this.people);
    }

    public boolean isValid() {
        List<Person> people = this.getPeople();
        Validator notAlone = new NotAloneWithRestrictionValidator(people);
        Validator prisonerWithoutPoliceman = new PrisonerWithoutPolicemanValidator(people);

        return notAlone.validate() && prisonerWithoutPoliceman.validate();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  PeopleWaiting) {
            PeopleWaiting other = (PeopleWaiting) obj;
            return Objects.equal(this.location, other.location) &&
                    Objects.equal(this.getPeople(), other.getPeople());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.location, this.getPeople());
    }
}
