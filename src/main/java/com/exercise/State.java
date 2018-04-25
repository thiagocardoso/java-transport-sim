package com.exercise;

import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.*;

import static com.google.common.base.MoreObjects.*;

public class State {
    private final List<PeopleWaiting> people = PeopleWaiting.forAllLocations();
    private final Place location;
    private State parent;
    private State next;

    State(Place location) {
        this.location = location;
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

        state.people
                .stream()
                .forEach(e ->
                    next.addPassengers(e.getLocation(), buildPeopleList(travel, e))
                );

        state.setNextState(next);
        return next;
    }

    private static List<Person> buildPeopleList(Travel travel, PeopleWaiting pw) {
        List<Person> list = Lists.newLinkedList(pw.getPeople());

        if (pw.getLocation() != travel.getOrigin()) {
            addVehiclePassengersToPeople(list, travel.getVehicle());
        } else {
            removeVehiclePassengersFromPeople((LinkedList)list, travel.getVehicle());
        }

        return list;
    }

    private static void addVehiclePassengersToPeople(List<Person> people, Vehicle vehicle) {
        people.add(vehicle.getDriver());

        if (vehicle.getPassenger() != null)
            people.add(vehicle.getPassenger());
    }

    private static void removeVehiclePassengersFromPeople(LinkedList<Person> people, Vehicle vehicle) {
        people.removeFirstOccurrence(vehicle.getDriver());

        if(vehicle.getPassenger() != null)
            people.removeFirstOccurrence(vehicle.getPassenger());
    }

    public List<Person> passengersWaitingAtLocation() {
        return this.passengersWaitingAt(this.location);
    }

    public List<Person> passengersWaitingAt(Place location) {
        return waitingAtLocation(location).getPeople();
    }

    public void addPassenger(Place location, Person person) {
        waitingAtLocation(location).addPeople(Lists.newArrayList(person));
    }

    public void addPassengers(Place location, List<Person> person) {
        waitingAtLocation(location).addPeople(person);
    }

    public boolean isValidState() {
        return people
                .stream()
                .allMatch(e -> e.isValid());
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

    public PeopleWaiting waitingAtCurrent() {
        return this.waitingAtLocation(this.location);
    }

    private PeopleWaiting waitingAtLocation(Place place) {
        return this.people
                .stream()
                .filter(p -> p.getLocation().equals(place))
                .findFirst()
                .orElse(null);
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
                .add("location", this.location)
                .add("people", this.people)
                .toString();
    }

}
