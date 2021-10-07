package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

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
        Person result = personDao.selectPerson(1);

        //Create role obj
        List<Person> persons = new ArrayList<Person>();
        persons.add(result);
        roleDao.addRoleAndAssignToPerson("Hecker", savedPersonId1);

        //Create contact information obj
        contactInformationDao.addContactInformation( "1111", "2222-3333", "gmail@gmail.com",savedPersonId1);

        assertTrue(result != null);
        assertEquals(result.getName(), testName);
    }

    @Test
    public void test_select_person_success(){
        RoleDao roleDao = new RoleDao();

        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        roleDao.addNewRole("Admin");
        roleDao.assignRoleToPerson(1, "Admin");

        //Get the person obj on database
        Person result = personDao.selectPerson(1);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        assertTrue(result != null);
        assertEquals(result.getName(), testName);
        assertEquals(result.getAddress(), testAddress);
        assertEquals(result.getGwa(), testGwa);
        assertEquals(result.getZipCode(), testZipcode);
        assertEquals(ft.format(result.getDateHired()), ft.format(testDate));
        assertEquals(result.getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
        assertEquals(result.getRoles().get(0).getRoleName(), "Admin");


        session.close();
        // assertEquals(expected, actual);
    }

    @Test
    public void test_update_person_success(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        String inputUpdateName = "Scooby Doobi Doo";
        String inputUpdateAddress = "Hallo Jo";
        double inputUpdateGwa = 5;
        String inputUpdateZipCode = "255";
        Date inputUpdateDate = new Date();
        boolean testIsCurrentlyEmployed = false;

        boolean isUpdated = personDao.updatePerson(inputUpdateGwa, inputUpdateZipCode, inputUpdateName, 
        inputUpdateAddress, inputUpdateDate, testIsCurrentlyEmployed, 1);

		Person results = personDao.selectPerson(1);

		assertTrue(isUpdated == true);
		assertEquals(results.getName(), inputUpdateName);
		assertEquals(results.getAddress(), inputUpdateAddress);
        assertEquals(results.getGwa(), inputUpdateGwa);
        assertEquals(results.getZipCode(), inputUpdateZipCode);
        assertEquals(results.getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
    }

    @Test 
    public void test_update_person_input_nonexistent_id_fail(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        String inputUpdateName = "Scooby Doobi Doo";
        String inputUpdateAddress = "Hallo Jo";
        double inputUpdateGwa = 5;
        String inputUpdateZipCode = "255";
        Date inputUpdateDate = new Date();
        boolean inputUpdateIsCurrentlyEmployed = false;
        int inputUpdateId = 10;

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personDao.updatePerson(inputUpdateGwa, inputUpdateZipCode, inputUpdateName, 
        inputUpdateAddress, inputUpdateDate, inputUpdateIsCurrentlyEmployed, inputUpdateId));

        Person results = personDao.selectPerson(1);

        assertEquals(exception.getMessage(), "Person id " + inputUpdateId + " does not exist");
        assertEquals(results.getName(), testName);
		assertEquals(results.getAddress(), testAddress);
        assertEquals(results.getGwa(), testGwa);
        assertEquals(results.getZipCode(), testZipcode);
        assertEquals(results.getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
    }

    @Test //TODO
    public void test_delete_person_success(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        boolean didDelete = personDao.deletePerson(1);

        assertTrue(didDelete == true);
    }

    @Test //TODO
    public void test_delete_person_input_nonexistent_id_fail(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        //Get the created person on db
        Person results = personDao.selectPerson(1);
        int nonExistentPersonId = 5;

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personDao.deletePerson(nonExistentPersonId));

        assertEquals(exception.getMessage(), "Person id " + nonExistentPersonId + " does not exist");
        assertTrue(results != null);
    }


    @Test //TODO
    public void test_select_nonexistent_personId_fail(){
        //Create person with id of 1
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed);

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personDao.selectPerson(5));

        System.out.println(exception.getMessage() != null);
    }

    @Test //TODO
    public void test_get_list_of_persons(){

    }

}
