package com.exercise;

import com.exercise.business.Manager;
import com.exercise.entity.Person;
import com.exercise.entity.State;
import com.exercise.travel.Place;
import org.junit.Test;

import java.util.List;

import static com.exercise.travel.Place.AIRPORT;
import static com.exercise.travel.Place.PLANE;
import static org.junit.Assert.*;

public class ManagerTest {
    @Test
    public void createWithInitialAndObjectiveState() {
        State initial = buildSimpleState(AIRPORT);
        State objective = buildSimpleState(PLANE);

        Manager manager = Manager.withStates(initial, objective);
        assertNotNull(manager);
        assertEquals(initial, manager.getInitialState());
        assertEquals(objective, manager.getObjectiveState());
    }

    @Test
    public void firstSolution_baseCase() {
        State initial = buildSimpleState(AIRPORT);
        State objective = buildSimpleState(PLANE);

        Manager manager = Manager.withStates(initial, objective);
        List<State> solution = manager.firstSolution();
        assertNotNull(solution);
        assertEquals(initial, solution.get(0));
        assertEquals(objective, solution.get(solution.size()-1));
    }

    @Test
    public void firstSolution() {
        State initial = buildExerciseState(AIRPORT);
        State objective = buildExerciseState(PLANE);

        Manager manager = Manager.withStates(initial, objective);
        List<State> solution = manager.firstSolution();
        assertNotNull(solution);
        assertEquals(initial, solution.get(0));
        assertEquals(objective, solution.get(solution.size()-1));
    }

    private State buildSimpleState(Place location) {
        State simple = State.of(location);
        simple.addPassenger(location, Person.newPilot());
        simple.addPassenger(location, Person.newOfficer());
        return simple;
    }

    private State buildExerciseState(Place location) {
        State state = State.of(location);
        state.addPassenger(location, Person.newPilot());
        state.addPassenger(location, Person.newOfficer());
        state.addPassenger(location, Person.newOfficer());

        state.addPassenger(location, Person.newOnboardChief());
        state.addPassenger(location, Person.newFlightAttendant());
        state.addPassenger(location, Person.newFlightAttendant());

        state.addPassenger(location, Person.newPoliceman());
        state.addPassenger(location, Person.newPrisoner());
        return state;
    }
}