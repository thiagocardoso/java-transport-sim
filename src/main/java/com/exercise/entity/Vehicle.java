package com.exercise.entity;

import com.exercise.entity.exception.VehicleWithoutDriverException;
import com.google.common.base.Objects;

public class Vehicle {
    private final Person passenger1;
    private final Person passenger2;

    public Vehicle(Person passenger1, Person passenger2) {
        if(passenger1 == null || !passenger1.canDrive())
            throw new VehicleWithoutDriverException();

        this.passenger1 = passenger1;
        this.passenger2 = passenger2;
    }

    public static Vehicle withPassengers(Person passenger1, Person passenger2) {
        return new Vehicle(passenger1, passenger2);
    }

    public boolean canTravel() {
        return !(passenger1.hasRestrictionWith(passenger2) || (passenger2 != null && passenger2.hasRestrictionWith(passenger1)));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vehicle) {
            Vehicle other = (Vehicle) o;
            return Objects.equal(passenger1, other.passenger1) &&
                    Objects.equal(passenger2, other.passenger2);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(passenger1, passenger2);
    }

    public Person getDriver() {
        return this.passenger1;
    }

    public Person getPassenger() {
        return this.passenger2;
    }
}
