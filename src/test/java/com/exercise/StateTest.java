package com.exercise;

import com.exercise.business.Guide;
import com.exercise.entity.Person;
import com.exercise.travel.Place;
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

        Travel travel = Guide.fromState(state).nextTravel();

        State next = State.buildNext(state, travel);

        assertNotNull(next.getParent());
        assertNotNull(state.getNextState());

        assertEquals(next, state.getNextState());
        assertEquals(state, next.getParent());

        assertEquals(0, next.passengersWaitingAt(state.getLocation()).size());
        assertEquals(2, next.passengersWaitingAtLocation().size());
    }

}