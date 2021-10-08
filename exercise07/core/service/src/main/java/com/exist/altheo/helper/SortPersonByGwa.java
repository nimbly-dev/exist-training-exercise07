package com.exist.altheo.helper;

import java.util.Comparator;

import com.exist.altheo.model.Person;

public class SortPersonByGwa implements Comparator<Person>{

    @Override
    public int compare(Person p1, Person p2) {
        return (int) (p1.getGwa() - p2.getGwa());
    }
    
}
