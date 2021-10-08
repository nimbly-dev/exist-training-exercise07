package com.exist.altheo.helper;

import java.util.Comparator;

import com.exist.altheo.model.Person;

public class SortPersonByLastName implements Comparator<Person>{

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getLastName().compareTo(p2.getLastName());
    }
    
}
