package com.exercise;

import com.exercise.entity.Person;
import com.exercise.entity.State;
import com.exercise.travel.Travel;
import org.junit.Test;

import java.util.List;

import static com.exercise.travel.Place.AIRPORT;
import static com.exercise.travel.Place.PLANE;
import static org.junit.Assert.*;

public class StateTest {
    @Test
    public void create() {
        State state = State.of(AIRPORT);
        assertNotNull(state);
    }

    @Test
    public void passengersWaiting_atAIRPORT() {
        State state = State.of(AIRPORT);
        state.addPassenger(AIRPORT, Person.newPilot());

        List<Person> people = state.passengersWaitingAt(AIRPORT);
        assertEquals(1, people.size());
    }

    @Test
    public void passengersWaiting_atPLANE() {
        State state = State.of(AIRPORT);
        state.addPassenger(PLANE, Person.newPilot());
        state.addPassenger(PLANE, Person.newOfficer());

        List<Person> people = state.passengersWaitingAt(PLANE);
        assertEquals(2, people.size());
    }

    @Test
    public void isValidState_true() {
        State state = State.of(AIRPORT);
        state.addPassenger(AIRPORT, Person.newPilot());
        state.addPassenger(AIRPORT, Person.newOfficer());

        assertTrue(state.isValidState());
    }

    @Test
    public void isValidState_false() {
        State state = State.of(AIRPORT);
        state.addPassenger(AIRPORT, Person.newPilot());
        state.addPassenger(AIRPORT, Person.newFlightAttendant());

        assertFalse(state.isValidState());
    }

    @Test
    public void assignParentState() {
        State parent = State.of(AIRPORT);

        State state = State.withParent(PLANE, parent);
        assertNotNull(state.getParent());
    }

    @Test
    public void assignNextState() {
        State state = State.of(AIRPORT);

        state.addPassenger(AIRPORT, Person.newOfficer());
        state.addPassenger(AIRPORT, Person.newPilot());

        Travel travel = travelBuilder(state).build();

        State next = State.buildNext(state, travel);

        assertNotNull(next.getParent());
        assertNotNull(state.getNextState());

        assertEquals(next, state.getNextState());
        assertEquals(state, next.getParent());

        assertEquals(0, next.passengersWaitingAt(state.getLocation()).size());
        assertEquals(2, next.passengersWaitingAtLocation().size());
    }

    @Test
    public void equalsState() {
        State state1 = State.of(AIRPORT);
        State state2 = State.of(AIRPORT);

        state1.addPassenger(AIRPORT, Person.newOfficer());
        state1.addPassenger(AIRPORT, Person.newPilot());

        state2.addPassenger(AIRPORT, Person.newOfficer());
        state2.addPassenger(AIRPORT, Person.newPilot());

        assertEquals(state1, state2);
    }

    @Test
    public void notEqualsState_differentLocation() {
        State state1 = State.of(AIRPORT);
        State state2 = State.of(PLANE);

        state1.addPassenger(AIRPORT, Person.newOfficer());
        state1.addPassenger(AIRPORT, Person.newPilot());

        state2.addPassenger(AIRPORT, Person.newOfficer());
        state2.addPassenger(AIRPORT, Person.newPilot());

        assertNotEquals(state1, state2);
    }

    @Test
    public void notEqualsState_differentPeople() {
        State state1 = State.of(AIRPORT);
        State state2 = State.of(AIRPORT);

        state1.addPassenger(AIRPORT, Person.newOfficer());
        state1.addPassenger(AIRPORT, Person.newPilot());

        state2.addPassenger(PLANE, Person.newOfficer());
        state2.addPassenger(PLANE, Person.newPilot());

        assertNotEquals(state1, state2);
    }

    @Test
    public void overflowSolutionByRedundanteTravel_regression() {
        State state1 = State.of(AIRPORT);
        State state2 = State.of(PLANE);

        state1.addPassenger(AIRPORT, Person.newOfficer());
        state1.addPassenger(AIRPORT, Person.newPilot());

        state2.addPassenger(PLANE, Person.newOfficer());
        state2.addPassenger(PLANE, Person.newPilot());

        Travel travel = travelBuilder(state1).build();

        State next = State.buildNext(state1, travel);
        assertEquals(state2, next);

        Travel travel2 = travelBuilder(state2).lastTravel(travel).build();
        next = State.buildNext(state2, travel2);
        assertNotEquals(state1, next);
    }

    private Travel.Builder travelBuilder(State state) {
        return Travel.Builder.from(state.waitingAtCurrent());
    }

}