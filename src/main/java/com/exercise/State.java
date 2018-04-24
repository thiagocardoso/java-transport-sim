package com.exercise;

import com.exercise.business.Guide;
import com.exercise.entity.Person;
import com.exercise.travel.Place;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class State {
    final Map<Place, List<Person>> people = Maps.newEnumMap(Place.class);

    State() {
        buildPeople();
    }

    public static final State of() {
        return new State();
    }

    public List<Person> passengersWaitingAt(Place location) {
        return ImmutableList.copyOf(this.people.get(location));
    }

    public void addPassenger(Place location, Person person) {
        this.people.get(location).add(person);
    }

    public boolean isValidState() {
        return people
                .entrySet()
                .stream()
                .allMatch(e -> Guide.of(e.getKey(), e.getValue()).isValid());
    }

    private void buildPeople() {
        for (Place place : Place.values())
            people.put(place, Lists.newLinkedList());
    }
}
