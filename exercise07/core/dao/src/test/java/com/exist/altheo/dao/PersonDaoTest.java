package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import junit.framework.TestCase;

public class PersonDaoTest extends TestCase {
    private PersonDao personDao;
    private RoleDao roleDao;
    private ContactInformationDao contactInformationDao;
    private SessionFactory sessionFactory;

    //Data testfields for person
    private String testFirstName;
	private String testLastName;
	private String testMiddleName;
	private String testTitle;
	private String testSuffix;
	private double testGwa;
	private String testZipcode;
	private String testAddress;
	private LocalDate testDate;
	private boolean testIsCurrentlyEmployed;
    
   
    @Override
    @BeforeAll
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.contactInformationDao = new ContactInformationDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
		this.personDao = new PersonDao();

		//Sets test values for test person obj
        this.testFirstName = "John";
		this.testMiddleName = "Doo";
		this.testLastName = "Doe";
		this.testSuffix = "Jr.";
		this.testTitle = "The Third";
		this.testGwa = 1.25;
		this.testZipcode = "Doo1";
		this.testAddress = "Winterfell, The North, Westeros";
		this.testDate = LocalDate.now();
		this.testIsCurrentlyEmployed = true;

    }

    @Override
    @AfterEach
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

    @Test
    public void test_add_person(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

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
        assertEquals(result.getFirstName(), testFirstName);
    }

    @Test //TODO - FIX BUG, STUCK ON EXECUTING TEST CASE UPON BULK TEST EXECUTION
    public void test_select_person_success() throws Exception{
        RoleDao roleDao = new RoleDao();

        //Create person with id of 1
        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, testFirstName, 
        testMiddleName, testLastName, testSuffix, testTitle);

        roleDao.addNewRole("Admin");
        roleDao.assignRoleToPerson(1, "Admin");

        //Get the person obj on database
        Person result = personDao.selectPerson(1);


        assertTrue(result != null);
        assertEquals(result.getFirstName(), testFirstName);
        assertEquals(result.getMiddleName(), testMiddleName);
        assertEquals(result.getLastName(), testLastName);
        assertEquals(result.getSuffix(), testSuffix);
        assertEquals(result.getTitle(), testTitle);
        assertEquals(result.getAddress(), testAddress);
        assertEquals(result.getGwa(), testGwa);
        assertEquals(result.getZipCode(), testZipcode);
        assertEquals(result.getDateHired(), testDate);
        assertEquals(result.getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
        assertEquals(result.getRoles().get(0).getRoleName(), "Admin");

        // assertEquals(expected, actual);
    }

    @Test
    public void test_update_person_success(){
        //Create person with id of 1
        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, testFirstName, 
        testMiddleName, testLastName, testSuffix, testTitle);

        String inputUpdateFirstName = "Scooby";
        String inputUpdateMiddle = "Doobi";
        String inputUpdateLastName = "Doo";
        String inputUpdateSuffix = "Jr.";
        String inputUpdateTitle = "Mystery Gang Member";
        String inputUpdateAddress = "Hallo Jo";
        double inputUpdateGwa = 5;
        String inputUpdateZipCode = "255";
        LocalDate inputUpdateDate = LocalDate.now();
        boolean testIsCurrentlyEmployed = false;

        personDao.updatePerson(inputUpdateAddress, inputUpdateGwa, inputUpdateZipCode, 
        inputUpdateDate, testIsCurrentlyEmployed, inputUpdateFirstName, 
        inputUpdateMiddle, inputUpdateLastName, inputUpdateSuffix, inputUpdateTitle, 1);

		Person results = personDao.selectPerson(1);

		
		assertEquals(results.getFirstName(), inputUpdateFirstName);
        assertEquals(results.getMiddleName(), inputUpdateMiddle);
        assertEquals(results.getLastName(), inputUpdateLastName);
        assertEquals(results.getSuffix(), inputUpdateSuffix);
        assertEquals(results.getTitle(), inputUpdateTitle);
		assertEquals(results.getAddress(), inputUpdateAddress);
        assertEquals(results.getGwa(), inputUpdateGwa);
        assertEquals(results.getZipCode(), inputUpdateZipCode);
        assertEquals(results.getIsCurrentlyEmployed(), testIsCurrentlyEmployed);
    }

    @Test //TODO - STUCK ON LOADING IF EXECUTED ON BULK TESTING
    public void test_update_person_input_nonexistent_id_fail(){

        String inputUpdateFirstName = "Scooby";
        String inputUpdateMiddle = "Doobi";
        String inputUpdateLastName = "Doo";
        String inputUpdateSuffix = "Jr.";
        String inputUpdateTitle = "Mystery Gang Member";
        String inputUpdateAddress = "Hallo Jo";
        double inputUpdateGwa = 5;
        String inputUpdateZipCode = "255";
        LocalDate inputUpdateDate = LocalDate.of(2021, 1, 2);
        boolean testIsCurrentlyEmployed = false;
        int inputUpdateId = 10;

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->  personDao.updatePerson(inputUpdateAddress, inputUpdateGwa, inputUpdateZipCode, 
        inputUpdateDate, testIsCurrentlyEmployed, inputUpdateFirstName, 
        inputUpdateMiddle, inputUpdateLastName, inputUpdateSuffix, inputUpdateTitle, inputUpdateId));

        assertEquals(exception.getMessage(), "Person id " + inputUpdateId + " does not exist");
    }

    @Test 
    public void test_delete_person_success(){
        //Create person with id of 1
        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, testFirstName, 
        testMiddleName, testLastName, testSuffix, testTitle);

        personDao.deletePerson(1);

    }

    @Test 
    public void test_delete_person_input_nonexistent_id_fail(){
        int nonExistentPersonId = 5;

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personDao.deletePerson(nonExistentPersonId));

        assertEquals(exception.getMessage(), "Person id " + nonExistentPersonId + " does not exist");
    }


    @Test 
    public void test_select_nonexistent_personId_fail(){
        //Create person with id of 1
        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, testFirstName, 
        testMiddleName, testLastName, testSuffix, testTitle);

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personDao.selectPerson(5));

        System.out.println(exception.getMessage() != null);
    }

    @Test 
    public void test_get_list_of_persons(){
        //Add persons to database
        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, testFirstName, 
        testMiddleName, testLastName, testSuffix, testTitle);

        personDao.addPerson(testAddress, testGwa, testZipcode, 
        testDate, testIsCurrentlyEmployed, "Another Person", 
        testMiddleName, testLastName, testSuffix, testTitle);

        List<Person> personList = personDao.getListPerson();

        assertTrue(personList.size() != 0);
        assertEquals(personList.get(0).getFirstName(), testFirstName);
        assertEquals(personList.get(1).getFirstName(), "Another Person");
    }

}
