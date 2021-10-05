package com.exist.altheo.model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class RoleTest extends TestCase{
      
    private Person person1;
    private Person person2;
    
    private SimpleDateFormat ft;
    private ZoneId defaultZoneId;
    private Date dateToday;
    private Date exampleDate;

    // private List<Person> persons;

    @SuppressWarnings("unchecked")
	@Override
	@BeforeEach
	protected void setUp() throws Exception {
        this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.defaultZoneId = ZoneId.systemDefault();
        this.dateToday = new Date();
        this.exampleDate = Date.from(LocalDate.of(2012, 12, 31).atStartOfDay(defaultZoneId).toInstant());

        List<Role> testRoles = new ArrayList<Role>();
        Set<ContactInformation> testContact = new HashSet();
		this.person1 = new Person(1.25, "322", "John Doe Doo Jr.", "Doobi Manila", 
        dateToday, true, testContact, testRoles);
        // new Person("John", "Doe", "Doo", "Jr", "King", 1.25, "7100", 
        // "Doobi", "Manila", "322", dateToday, true, testContact, 
        // testRoles);

        this.person2 = 
        new Person(5, "322", "Scooby Doobo Doo Sr.", "Sne Makati", 
        dateToday, true, testContact, testRoles);
        // new Person("Scooby", "Doobo", "Doo", "", "Dog", 5 , "7200", 
        // "Sne", "Makati", "122", exampleDate, true, testContact, 
        // testRoles);
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
       assertTrue(hacker.getPersons().get(0).getName() == "John Doe Doo Jr.");
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
        assertTrue(hacker.getPersons().get(1).getName() == "Scooby Doobo Doo Sr.");
    }

}
