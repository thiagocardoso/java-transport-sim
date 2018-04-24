package com.exercise.entity;

import com.exercise.entity.Person;
import com.exercise.entity.Vehicle;
import com.exercise.entity.exception.VehicleWithoutDriverException;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTest {
    @Test
    public void create() {
        Vehicle vehicle = buildValidVehicle();
        assertNotNull(vehicle);
    }

    @Test
    public void isValidAgainstRestrictions() {
        Vehicle vehicle = buildValidVehicle();
        assertTrue(vehicle.canTravel());
    }

    @Test
    public void isNotValidAgainstRestrictions() {
        Vehicle vehicle = buildInvalidVehicle();
        assertFalse(vehicle.canTravel());
    }

    @Test(expected = VehicleWithoutDriverException.class)
    public void cantCreateVehicleWithoutDriver() {
        Vehicle.withPassengers(null, Person.newOfficer());
    }

    @Test(expected = VehicleWithoutDriverException.class)
    public void driverMustHaveCanDriveTrue() {
        Vehicle.withPassengers(Person.newOfficer(), Person.newOfficer());
    }

    @Test
    public void canCreateVehicleWithoutPassenger() {
        assertNotNull(Vehicle.withPassengers(Person.newPilot(), null));
    }

    private Vehicle buildValidVehicle() {
        Person passenger1 = Person.newPilot();
        Person passenger2 = Person.newOfficer();
        return Vehicle.withPassengers(passenger1, passenger2);
    }

    private Vehicle buildInvalidVehicle() {
        Person passenger1 = Person.newPilot();
        Person passenger2 = Person.newFlightAttendant();
        return Vehicle.withPassengers(passenger1, passenger2);
    }
}