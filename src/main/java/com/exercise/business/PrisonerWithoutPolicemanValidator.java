package com.exercise.business;

import com.exercise.entity.Person;
import com.exercise.role.BasicRole;
import com.exercise.role.Role;

import java.util.List;

import static com.exercise.role.BasicRole.POLICEMAN;
import static com.exercise.role.BasicRole.PRISONER;

public class PrisonerWithoutPolicemanValidator implements Validator{
    final List<Person> people;

    public PrisonerWithoutPolicemanValidator(List<Person> people) {
        this.people = people;
    }

    public boolean validate() {
        if (people.size() <= 1)
            return true;

        return !(hasRole(PRISONER) && !hasRole(POLICEMAN));
    }

    private boolean hasRole(Role role) {
        return people.stream().anyMatch(person -> person.getRole() == role);
    }
}