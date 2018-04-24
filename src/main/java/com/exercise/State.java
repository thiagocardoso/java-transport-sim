package com.exercise;

import com.exercise.business.Guide;
import com.exercise.entity.Person;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.MoreObjects.*;

public class State {
    final Map<Place, List<Person>> people = Maps.newEnumMap(Place.class);
    private final Place location;
    private State parent;
    private State next;

    State(Place location) {
        this.location = location;
        buildPeople();
    }

    public State(Place location, State parent) {
        this(location);
        this.parent = parent;
    }

    public static final State of(Place location) {
        return new State(location);
    }

    public static State withParent(Place location, State parent) {
        return new State(location, parent);
    }

    public static State buildNext(State state, Travel travel) {
        State next = withParent(travel.getDestination(), state);

        state.people.entrySet()
                .stream()
                .forEach(e ->
                    next.addPassengers(e.getKey(), buildPeopleList(travel, e))
                );

        state.setNextState(next);
        return next;
    }

    private static List<Person> buildPeopleList(Travel travel, Map.Entry<Place, List<Person>> entry) {
        List<Person> list = entry.getValue();

        if (entry.getKey() != travel.getOrigin()) {
            list.addAll(Lists.newArrayList(travel.getVehicle().getDriver(), travel.getVehicle().getPassenger()));
        } else {
            list = entry.getValue().stream()
                    .filter(p -> !travel.getVehicle().isPassengerInto(p))
                    .collect(Collectors.toList());
        }

        return list;
    }

    public List<Person> passengersWaitingAtLocation() {
        return this.passengersWaitingAt(this.location);
    }

    public List<Person> passengersWaitingAt(Place location) {
        return ImmutableList.copyOf(this.people.get(location));
    }

    public void addPassenger(Place location, Person person) {
        this.people.get(location).add(person);
    }

    public void addPassengers(Place location, Collection<? extends Person> person) {
        this.people.get(location).addAll(person);
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

    public State getParent() {
        return this.parent;
    }

    public void setNextState(State next) {
        this.next = next;
    }

    public State getNextState() {
        return this.next;
    }

    public Place getLocation() {
        return this.location;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State) {
            State other = (State) obj;
            return Objects.equal(this.location, other.location) && Objects.equal(this.people, other.people);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location, people);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("parent", this.parent)
                .add("next", this.next)
                .add("people", this.people)
                .toString();
    }
}
