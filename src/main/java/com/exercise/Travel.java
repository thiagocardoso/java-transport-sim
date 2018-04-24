package com.exercise;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

public class Travel {
    private final Vehicle vehicle;
    private final Direction origin;
    private final Direction destination;

    public Travel(Vehicle vehicle, Direction origin, Direction destination) {
        checkNotNull(vehicle);
        checkNotNull(origin);
        checkNotNull(destination);

        this.vehicle = vehicle;
        this.origin = origin;
        this.destination = destination;
    }

    public static Travel with(Vehicle vehicle, Direction origin, Direction destination) {
        return new Travel(vehicle, origin, destination);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Direction getOrigin() {
        return origin;
    }

    public Direction getDestination() {
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
