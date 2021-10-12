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
        "Doobi Manila", dateToday,LocalDate.of(2012, 12, 1) ,true);

        this.person2 = new Person(5, "322", "Scooby", "Doobo", "Doo", "Sr.", 
        "Dog", "Makati", dateToday, LocalDate.of(2012, 12, 1),true);
	}

    @Test
    public void test_role_getters(){
       List<Person> persons = new ArrayList<Person>();
       persons.add(this.person1);

       Role admin = new Role("Admin");
        // System.out.println(persons.size());

       assertTrue(admin.getRoleName() == "Admin");

    }

    @Test
    public void test_role_setters(){
        List<Person> persons = new ArrayList<Person>();
        persons.add(person1);

        List<Person> newPersonList = new ArrayList<Person>();
        newPersonList.add(person1);
        newPersonList.add(person2);
 
        Role admin = new Role("Admin");

        admin.setRoleName("Super Admin");

        assertTrue(admin.getRoleName() == "Super Admin");
 
    }

}
