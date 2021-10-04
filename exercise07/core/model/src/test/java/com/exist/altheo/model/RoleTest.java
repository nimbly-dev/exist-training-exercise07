package com.exist.altheo.model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class RoleTest extends TestCase{
      
    private Person person1;
    
    private SimpleDateFormat ft;
    private ZoneId defaultZoneId;
    private Date dateToday;
    private Date exampleDate;

    private List<Person> persons;

    @SuppressWarnings("unchecked")
	@Override
	@Before
	protected void setUp() throws Exception {
        this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.defaultZoneId = ZoneId.systemDefault();
        this.dateToday = new Date();
        this.exampleDate = Date.from(LocalDate.of(2012, 12, 31).atStartOfDay(defaultZoneId).toInstant());

        List<Role> testRoles = new ArrayList<Role>();
        Set<ContactInformation> testContact = new HashSet();
		person1 = new Person("John", "Doe", "Doo", "Jr", 
        "King", dateToday , true , testContact, testRoles);

        this.persons = new ArrayList<Person>();
        persons.add(person1);

	}

    @Test
    public void test_role_getters(){
       Role admin = new Role("Admin");
       Role hacker = new Role("Hecker", persons);

       System.out.println(persons.size());

       assertTrue(admin.getRoleName() == "Admin");
       
       assertTrue(hacker.getRoleName() == "Hecker");
       assertTrue(hacker.getPersons().size() == 1);
       assertTrue(hacker.getPersons().get(0).getFirstName() == "John");
    }


}
