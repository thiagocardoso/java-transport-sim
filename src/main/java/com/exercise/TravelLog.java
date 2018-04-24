package com.exercise;

import com.google.common.collect.Lists;

import java.util.List;

public class TravelLog {
    public static final List<Travel> log = Lists.newLinkedList();

    public static final TravelLog of() {
        return new TravelLog();
    }

    public void addTravel(Travel travel) {
        this.log.add(travel);
    }

    public boolean hasTravels() {
        return !log.isEmpty();
    }
}