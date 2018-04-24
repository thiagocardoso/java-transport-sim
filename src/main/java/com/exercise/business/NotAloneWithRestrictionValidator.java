package com.exercise.business;

import com.exercise.entity.Person;

import java.util.List;

class NotAloneWithRestrictionValidator implements Validator{
    final List<Person> people;

    NotAloneWithRestrictionValidator(List<Person> people) {
        this.people = people;
    }

    public boolean validate() {
        if (people.size() != 2)
            return true;

        Person person1 = people.get(0);
        Person person2 = people.get(1);

        return !(person1.hasRestrictionWith(person2) || person2.hasRestrictionWith(person1));
    }
}