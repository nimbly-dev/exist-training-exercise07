package com.exist.altheo.dao;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class PersonDaoTest extends TestCase {
    private PersonDao personDao;
    private RoleDao roleDao;
    private ContactInformationDao contactInformationDao;
    private SessionFactory sessionFactory;

    //Data testfields for person
	private String testName;
	private double testGwa;
	private String testZipcode;
	private String testAddress;
	private Date testDate;
	private boolean testIsCurrentlyEmployed;
	private Set<ContactInformation> testContactInformations;
	private List<Role> testRoles;

    
    private SimpleDateFormat ft;
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.contactInformationDao = new ContactInformationDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
		this.personDao = new PersonDao();
        this.ft = new SimpleDateFormat ("yyyy-MM-dd");
        ZoneId.systemDefault();

		//Sets test values for test person obj
		this.testName= "John Doe Doo Jr.";
		this.testGwa = 1.25;
		this.testZipcode = "Doo1";
		this.testAddress = "Winterfell, The North, Westeros";
		this.testDate = new Date();
		this.testIsCurrentlyEmployed = true;

		this.testContactInformations = new HashSet<ContactInformation>(0);
		this.testRoles = new ArrayList<Role>();
    }

    @Test
    public void test_add_person(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

        //Get the person obj on database
        List<Person> result = personDao.selectPerson(1);

        //Create role obj
        List<Person> persons = new ArrayList<Person>();
        persons.add(result.get(0));
        roleDao.addRole("Hecker", savedPersonId1);

        //Create contact information obj
        contactInformationDao.addContactInformation( "1111", "2222-3333", "gmail@gmail.com", result.get(0));

        assertTrue(result.get(0) != null);
        assertEquals(result.get(0).getName(), testName);
    }

    @Test
    public void test_select_person_success(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);

        //Get the person obj on database
        List<Person> result = personDao.selectPerson(1);

        Session session = sessionFactory.openSession();

        assertTrue(result.get(0) != null);
        assertEquals(result.get(0).getName(), testName);
        assertEquals(result.get(0).getAddress(), testAddress);
        assertEquals(result.get(0).getGwa(), testGwa);
        assertEquals(result.get(0).getZipCode(), testZipcode);
        assertEquals(ft.format(result.get(0).getDateHired()), ft.format(testDate));
        assertEquals(result.get(0).getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
        assertEquals(result.get(0).getContactInformations(), testContactInformations);
        assertEquals(result.get(0).getRoles(), testRoles);

        session.close();
        // assertEquals(expected, actual);
    }
}
