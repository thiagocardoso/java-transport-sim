package com.exercise.travel;

import static com.exercise.travel.Place.destByOrigin;
import static com.google.common.base.Preconditions.checkNotNull;

import com.exercise.PeopleWaiting;
import com.exercise.business.DriverNotFoundException;
import com.exercise.business.PassengerNotFoundForDriverException;
import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Travel {
    public static class Builder {
        private Travel lastTravel;
        private final PeopleWaiting peopleWaiting;
        private boolean onlyDriver;

        Builder(PeopleWaiting peopleWaiting) {
            this.peopleWaiting = peopleWaiting;
        }

        public static final Builder from(PeopleWaiting peopleWaiting) {
            return new Builder(peopleWaiting);
        }

        public Builder lastTravel(Travel lastTravel){
            this.lastTravel = lastTravel;
            return this;
        }

        public Builder onlyDriver(boolean onlyDriver) {
            this.onlyDriver = onlyDriver;
            return this;
        }

        public Travel build() {
            if (this.onlyDriver)
                return buildOnlyDriver();

            List<Person> passengers = Lists.newLinkedList(peopleWaiting.getPeople());

            Person driver = nextDriver();
            Person passenger = nextPassengerOrNull(driver, passengers);
            Vehicle vehicle = Vehicle.withPassengers(driver, passenger);

            while (lastTravel != null && lastTravel.getVehicle().equals(vehicle)) {
                passengers.remove(passenger);
                driver = nextDriver();
                passenger = nextPassengerOrNull(driver, passengers);
                vehicle = Vehicle.withPassengers(driver, passenger);
            }

            return Travel.with(vehicle, peopleWaiting.getLocation(), destByOrigin(peopleWaiting.getLocation()));
        }

        private Travel buildOnlyDriver() {
            Vehicle vehicle = Vehicle.withPassengers(nextDriver(), null);
            return Travel.with(vehicle, peopleWaiting.getLocation(), destByOrigin(peopleWaiting.getLocation()));
        }

        private Person nextDriver() {
            Random r = new Random();
            List<Person> drivers = getDrivers();

            if(drivers.isEmpty())
                throw new DriverNotFoundException();

            return drivers.get(r.nextInt(drivers.size()));
        }

        private List<Person> getDrivers() {
            return peopleWaiting.getPeople()
                        .stream()
                        .filter(p -> p.canDrive())
                        .collect(Collectors.toList());
        }

        private Person nextPassengerOrNull(Person driver, List<Person> availablePassengers) {
            try{
                return nextPassengerByDriver(driver, availablePassengers);
            }catch (PassengerNotFoundForDriverException e) {
                return null;
            }
        }

        private Person nextPassengerByDriver(Person driver, List<Person> availablePassengers) {
            for (Person p: availablePassengers) {
                if(!p.equals(driver) && !driver.hasRestrictionWith(p))
                    return p;
            }

            throw new PassengerNotFoundForDriverException();
        }
    }

    private final Vehicle vehicle;
    private final Place origin;
    private final Place destination;

    Travel(Vehicle vehicle, Place origin, Place destination) {
        checkNotNull(vehicle);
        checkNotNull(origin);
        checkNotNull(destination);

        this.vehicle = vehicle;
        this.origin = origin;
        this.destination = destination;
    }

    public static final Travel with(Vehicle vehicle, Place origin, Place destination) {
        return new Travel(vehicle, origin, destination);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Place getOrigin() {
        return origin;
    }

    public Place getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Travel){
            Travel other = (Travel) o;
            return Objects.equal(vehicle, other.vehicle) &&
                    origin == other.origin &&
                    destination == other.destination;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vehicle, origin, destination);
    }
}
