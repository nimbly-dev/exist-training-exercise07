package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import javax.persistence.NoResultException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import junit.framework.TestCase;

public class ContactInformationDaoTest extends TestCase {

    private PersonDao personDao;
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
    private LocalDate testBirthDate;
    private boolean testIsCurrentlyEmployed;
    //Data testfields for contact
    private String testLandline;
    private String testMobileNum;
    private String testEmail;

    @Override
    @BeforeAll
    protected void setUp() throws Exception {
        this.contactInformationDao = new ContactInformationDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
        this.personDao = new PersonDao();

		DBConnection.flushDbTables(sessionFactory);
		DBConnection.executeStartingSQLScript(sessionFactory);

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
        this.testBirthDate = LocalDate.of(2012, 12, 30);
        this.testIsCurrentlyEmployed = true;

        
        //Sets test values for test contact obj
        this.testLandline = "1111";
        this.testMobileNum = "2222-3333";
        this.testEmail = "gmail@gmailing.com";
    }

    @Override
    @AfterEach
	protected void tearDown() throws Exception {
		DBConnection.flushDbTables(sessionFactory);
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}
    
    @Test //TODO - FIX BUG, STUCK ON EXECUTING TEST CASE UPON BULK TEST EXECUTION
    public void test_add_contact_information_to_person_success() {
        //Add a person obj first
        Session session = sessionFactory.openSession();

        session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate,testBirthDate ,testIsCurrentlyEmployed));

        session.getTransaction().commit();
        session.close();
        
        //Add contact information to person 
        contactInformationDao.addContactInformation(testLandline, 
        testMobileNum, testEmail, savedPersonId1);

        // Check the newly created contactInformation
        ContactInformation contactInformation = contactInformationDao.selectContact(1);

		assertEquals(contactInformation.getLandline(), testLandline);
        assertEquals(contactInformation.getMobileNumber(), testMobileNum);
        assertEquals(contactInformation.getEmail(), testEmail);
    }

    @Test
    public void test_update_contact_information_success() {
        //Add a person obj first
        Session session = sessionFactory.openSession();

        session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate,testBirthDate ,testIsCurrentlyEmployed));

        session.getTransaction().commit();
        session.close();

        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail, savedPersonId1);

        int testSelectedContactId = 1;
        String testUpdateLandline = "2000";
        String testUpdateMobileNum = "8-7000";
        String testUpdateEmail = "feedback@jfc.com.ph";

        contactInformationDao.updateContactInformation(
            testSelectedContactId, testUpdateLandline, testUpdateMobileNum,
            testUpdateEmail);

        ContactInformation contact = contactInformationDao.selectContact(1);

        assertEquals(contact.getLandline(), testUpdateLandline);
        assertEquals(contact.getMobileNumber(), testUpdateMobileNum);
        assertEquals(contact.getEmail(), testUpdateEmail);
    }

    @Test
    public void test_update_contact_information_with_non_existent_contact_obj_fail() {      
        //Add a person obj first
        Session session = sessionFactory.openSession();

        session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate,testBirthDate ,testIsCurrentlyEmployed));

        session.getTransaction().commit();
        session.close();

        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail, savedPersonId1);

        int testSelectedContactId = 2;
        String testUpdateLandline = "2000";
        String testUpdateMobileNum = "8-7000";
        String testUpdateEmail = "feedback@jfc.com.ph";

        //Delete the obj
        NoResultException exception = assertThrows(NoResultException.class, 
        ()-> 
        contactInformationDao.updateContactInformation(
             testSelectedContactId, testUpdateLandline, testUpdateMobileNum,
             testUpdateEmail));

        assertEquals(exception.getMessage(), "Person id " + testSelectedContactId + " does not exist");

        ContactInformation contact = contactInformationDao.selectContact(1);

        //Checks if values did not changed
        assertEquals(contact.getLandline(), testLandline);
        assertEquals(contact.getMobileNumber(), testMobileNum);
        assertEquals(contact.getEmail(), testEmail);
    }

    @Test
    public void test_delete_contact_information_success() {
        //Add a person obj first
        Session session = sessionFactory.openSession();

        session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate,testBirthDate ,testIsCurrentlyEmployed));

        session.getTransaction().commit();
        session.close();

        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail,savedPersonId1);
        int testSelectedContactId = 1;

        //Delete the obj
        contactInformationDao.deleteContact(testSelectedContactId);
        
    }

    @Test
    public void test_delete_contact_information_with_nonexistent_id_fail() {
        int testSelectedContactId = 2;

        //Delete the obj
        NoResultException exception = assertThrows(NoResultException.class, 
        ()-> contactInformationDao.deleteContact(testSelectedContactId));

        assertEquals(exception.getMessage(), "person id " + testSelectedContactId + " does not exist");
    }
}
