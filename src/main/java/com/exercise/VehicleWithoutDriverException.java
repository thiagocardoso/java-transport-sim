package com.exercise;

public class VehicleWithoutDriverException extends RuntimeException{
    public VehicleWithoutDriverException() {
        super("A vehicle must have a passenger that can drive it!");
    }
}
