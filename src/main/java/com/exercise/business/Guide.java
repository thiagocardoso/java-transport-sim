package com.exercise.business;

import com.exercise.State;
import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class Guide {

    private final List<Person> availablePassengers;
    private final Place currentPosition;

    Guide() {
        this(null, null);
    }

    Guide(Place currentPosition, List<Person> availablePassengers) {
        this.currentPosition = currentPosition;
        this.availablePassengers = ImmutableList.copyOf(availablePassengers);
    }

    public static final Guide of(Place currentPosition, List<Person> availablePassengers) {
        return new Guide(currentPosition, availablePassengers);
    }

    public static Guide fromState(State state) {
        return new Guide(state.getLocation(), state.passengersWaitingAt(state.getLocation()));
    }

    public Travel nextTravel() {
        return this.nextTravel(null);
    }

    public Travel nextTravel(Travel lastTravel) {
        List<Person> passengers = Lists.newLinkedList(this.availablePassengers);

        Person passenger = nextPassengerForCurrentDriver(passengers);
        Vehicle vehicle = Vehicle.withPassengers(nextDriver(), passenger);

        while (lastTravel != null && lastTravel.getVehicle().equals(vehicle)) {
            passengers.remove(passenger);

            passenger = nextPassengerForCurrentDriver(passengers);
            vehicle = Vehicle.withPassengers(nextDriver(), passenger);
        }

        return Travel.with(vehicle, currentPosition, Place.destByOrigin(currentPosition));
    }

    public Person nextDriver() {
        for (Person p: availablePassengers)
            if(p.canDrive())
                return p;

        throw new DriverNotFoundException();
    }

    public Person nextPassengerByDriver(Person driver, List<Person> availablePassengers) {
        for (Person p: availablePassengers) {
            if(!p.canDrive() && !p.equals(driver) && !driver.hasRestrictionWith(p))
                return p;
        }

        throw new PassengerNotFoundForDriverException();
    }

    private Person nextPassengerForCurrentDriver(List<Person> availablePassengers) {
        try{
            Person driver = nextDriver();
            return nextPassengerByDriver(driver, availablePassengers);
        }catch (PassengerNotFoundForDriverException e) {
            return null;
        }
    }

    public boolean isValid() {
        Validator notAlone = new NotAloneWithRestrictionValidator(this.availablePassengers);
        Validator prisonerWithoutPoliceman = new PrisonerWithoutPolicemanValidator(this.availablePassengers);

        return notAlone.validate() && prisonerWithoutPoliceman.validate();
    }

    public class DriverNotFoundException extends RuntimeException {}
    public class PassengerNotFoundForDriverException extends RuntimeException {}
}
