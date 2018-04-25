package com.exercise.business;

import com.exercise.State;
import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.travel.Place;
import com.exercise.travel.Travel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        Person driver = nextDriver();
        Person passenger = nextPassengerOrNull(driver, passengers);
        Vehicle vehicle = Vehicle.withPassengers(driver, passenger);

        while (lastTravel != null && lastTravel.getVehicle().equals(vehicle)) {
            passengers.remove(passenger);
            driver = nextDriver();
            passenger = nextPassengerOrNull(driver, passengers);
            vehicle = Vehicle.withPassengers(driver, passenger);
        }

        return Travel.with(vehicle, currentPosition, Place.destByOrigin(currentPosition));
    }

    public Travel nextTravelOnlyDriver() {
        Vehicle vehicle = Vehicle.withPassengers(nextDriver(), null);
        return Travel.with(vehicle, currentPosition, Place.destByOrigin(currentPosition));
    }

    public Person nextDriver() {
        Random r = new Random();
        List<Person> drivers = getDrivers();
        return drivers.get(r.nextInt(drivers.size()));

//        return availablePassengers.stream().filter(p -> p.canDrive()).findAny().orElseThrow(() -> new DriverNotFoundException());

//        for (Person p: availablePassengers)
//            if(p.canDrive())
//                return p;
//
//        throw new DriverNotFoundException();
    }

    private List<Person> getDrivers() {
        return availablePassengers.stream().filter(p -> p.canDrive()).collect(Collectors.toList());
    }

    public Person nextPassengerByDriver(Person driver, List<Person> availablePassengers) {
        for (Person p: availablePassengers) {
            if(!p.equals(driver) && !driver.hasRestrictionWith(p))
                return p;
        }

        throw new PassengerNotFoundForDriverException();
    }

    private Person nextPassengerOrNull(Person driver, List<Person> availablePassengers) {
        try{
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
