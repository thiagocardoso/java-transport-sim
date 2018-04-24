package com.exercise.business;

import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.ImmutableList;

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

    public Travel nextTravel() {
        Person passenger = nextPassengerForCurrentDriver();

        Vehicle vehicle = Vehicle.withPassengers(nextDriver(), passenger);
        return Travel.with(vehicle, currentPosition, Place.destByOrigin(currentPosition));
    }

    public Person nextDriver() {
        for (Person p: availablePassengers)
            if(p.canDrive())
                return p;

        throw new DriverNotFoundException();
    }

    public Person nextPassengerByDriver(Person driver) {
        for (Person p: availablePassengers) {
            if(!p.equals(driver) && !driver.hasRestrictionWith(p))
                return p;
        }

        throw new PassengerNotFoundForDriverException();
    }

    private Person nextPassengerForCurrentDriver() {
        try{
            return nextPassengerByDriver(nextDriver());
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
