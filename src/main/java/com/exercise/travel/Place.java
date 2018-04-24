package com.exercise.travel;

public enum Place {
    PLANE,
    AIRPORT;

    public static final Place destByOrigin(Place origin) {
        return (origin == PLANE)? AIRPORT : PLANE;
    }
}
