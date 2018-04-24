package com.exercise;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void testCreate() {
        Person person = new Person();
        assertNotNull(person);
    }

    @Test
    public void testCannotDrive() {
        Person person = new Person();
        assertFalse(person.canDrive());
    }

    @Test
    public void testCanDrive(){
        Person person = new Driver();
        assertTrue(person.canDrive());
    }


}