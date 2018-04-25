package com.exercise;

import com.exercise.business.Guide;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.logging.Logger;

public class Manager {
    private static final Logger LOGGER= Logger.getLogger(Manager.class.getName());

    private final State initialState;
    private final State objectiveState;
    private final List<State> solution = Lists.newLinkedList();

    Manager(State initial, State objective) {
        this.initialState = initial;
        this.objectiveState = objective;
    }

    public static Manager withStates(State initial, State objective) {
        return new Manager(initial, objective);
    }

    public State getInitialState() {
        return this.initialState;
    }

    public State getObjectiveState() {
        return objectiveState;
    }

    public List<State> firstSolution() {
        process(initialState, null, this.solution);
        return this.solution;
    }

    public State process(State state, Travel lastTravel, List<State> actualSolution) {
        if (state != null && !actualSolution.contains(state)) {
            printLog(state);
            actualSolution.add(state);
            Travel travel = Guide.fromState(state).nextTravel(lastTravel);
            State next = State.buildNext(state, travel);

            if (objectiveState.equals(next))
                return next;

            return process(next, travel, actualSolution);
        }
        return null;
    }

    private void printLog(State state) {
//        System.out.println("--------------------");
        System.out.println(String.format("[] Actual Location: %s", state.getLocation()));
        System.out.println(String.format("[] AIRPORT: %s", state.passengersWaitingAt(Place.AIRPORT)));
        System.out.println(String.format("[] PLANE: %s", state.passengersWaitingAt(Place.PLANE)));
        System.out.println("--------------------");
    }
}
