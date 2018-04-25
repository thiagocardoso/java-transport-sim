package com.exercise.business;

import com.exercise.entity.State;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.Lists;

import java.util.List;

public class Manager {
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
        if (state != null && state.isValidState()) {
            printLog(state);
            actualSolution.add(state);

            if (objectiveState.equals(state))
                return state;

            Travel travel = buildTravel(state, lastTravel);
            State next = State.buildNext(state, travel);

            while (actualSolution.contains(next) || !next.isValidState()) {
                travel = buildTravel(state, travel);
                next = State.buildNext(state, travel);
            }

            return process(next, travel, actualSolution);
        }
        return null;
    }

    private Travel buildTravel(State state, Travel lastTravel) {
        Travel.Builder builder = Travel.Builder.from(state.waitingAtCurrent());

        return builder
                .lastTravel(lastTravel)
                .onlyDriver(state.getLocation() == objectiveState.getLocation())
                .build();
    }



    private void printLog(State state) {
        System.out.println(String.format("[] Actual Location: %s", state.getLocation()));
        System.out.println(String.format("[] AIRPORT: %s", state.passengersWaitingAt(Place.AIRPORT)));
        System.out.println(String.format("[] PLANE: %s", state.passengersWaitingAt(Place.PLANE)));
        System.out.println("--------------------");
    }
}
