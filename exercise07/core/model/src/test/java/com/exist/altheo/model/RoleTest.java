package com.exist.altheo.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class RoleTest extends TestCase{
      
    private Person person1;
    private Person person2;
    
    // private SimpleDateFormat ft;
    // private ZoneId defaultZoneId;
    private LocalDate dateToday;
    // private Date exampleDate;

    // private List<Person> persons;

	@Override
	@BeforeEach
	protected void setUp() throws Exception {
        // this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        // this.defaultZoneId = ZoneId.systemDefault();
        this.dateToday = LocalDate.now();
        // this.exampleDate = Date.from(LocalDate.of(2012, 12, 31).atStartOfDay(defaultZoneId).toInstant());

        this.person1 = new Person(1.25, "322", "John", "Doe", "Doo", "Jr.", "The Third", 
        "Doobi Manila", dateToday, true);

        this.person2 = new Person(5, "322", "Scooby", "Doobo", "Doo", "Sr.", 
        "Dog", "Makati", dateToday, true);
	}

    @Test
    public void test_role_getters(){
       List<Person> persons = new ArrayList<Person>();
       persons.add(this.person1);

       Role admin = new Role("Admin");
       Role hacker = new Role("Hecker", persons);

        // System.out.println(persons.size());

       assertTrue(admin.getRoleName() == "Admin");

       assertTrue(hacker.getRoleName() == "Hecker");
       assertTrue(hacker.getPersons().size() == 1);
    }

    @Test
    public void test_role_setters(){
        List<Person> persons = new ArrayList<Person>();
        persons.add(person1);

        List<Person> newPersonList = new ArrayList<Person>();
        newPersonList.add(person1);
        newPersonList.add(person2);
 
        Role admin = new Role("Admin");
        Role hacker = new Role("Hecker", persons);

        admin.setRoleName("Super Admin");

        hacker.setRoleName("Hacker");
        hacker.setPersons(newPersonList);

        assertTrue(admin.getRoleName() == "Super Admin");

        assertTrue(hacker.getRoleName() == "Hacker");
        assertTrue(hacker.getPersons().size() == 2);
        assertTrue(hacker.getPersons().get(1).getFirstName() == "Scooby");
    }

}
