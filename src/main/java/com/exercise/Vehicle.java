package com.exercise;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

public class Vehicle {
    private final Person passenger1;
    private final Person passenger2;

    public Vehicle(Person passenger1, Person passenger2) {
        checkNotNull(passenger1);
        checkNotNull(passenger2);

        this.passenger1 = passenger1;
        this.passenger2 = passenger2;
    }

    public static Vehicle withPassengers(Person passenger1, Person passenger2) {
        return new Vehicle(passenger1, passenger2);
    }

    public boolean canTravel() {
        return !(passenger1.hasRestrictionWith(passenger2) || passenger2.hasRestrictionWith(passenger1));
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
}
