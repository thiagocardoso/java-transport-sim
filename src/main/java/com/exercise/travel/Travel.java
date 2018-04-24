package com.exercise.travel;

import static com.google.common.base.Preconditions.checkNotNull;

import com.exercise.entity.Vehicle;
import com.google.common.base.Objects;

public class Travel {
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
