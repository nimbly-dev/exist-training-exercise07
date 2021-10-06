package com.exist.altheo.dao;

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
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class ContactInformationDaoTest extends TestCase {

    private PersonDao personDao;
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

    //Data testfields for contact
    private String testLandline;
    private String testMobileNum;
    private String testEmail;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.contactInformationDao = new ContactInformationDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
        this.personDao = new PersonDao();

        //Sets test values for test person obj
        this.testName= "John Doe Doo Jr.";
        this.testGwa = 1.25;
        this.testZipcode = "Doo1";
        this.testAddress = "Winterfell, The North, Westeros";
        this.testDate = new Date();
        this.testIsCurrentlyEmployed = true;

        this.testContactInformations = new HashSet<ContactInformation>(0);
        this.testRoles =new ArrayList<Role>();
        
        //Sets test values for test contact obj
        this.testLandline = "1111";
        this.testMobileNum = "2222-3333";
        this.testEmail = "gmail@gmailing.com";
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void test_add_contact_information_success() {
        String hsql = "FROM ContactInformation C WHERE C.email = '"+testEmail+"'";

        //Add Person Obj first
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);


        //Add contact information to person 
        //With id of 1
        List<Person> result = personDao.selectPerson(1);
        contactInformationDao.addContactInformation(testLandline, 
        testMobileNum, testEmail, result.get(0));

        //Main Testcase
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query<ContactInformation> query = session.createQuery(hsql);

		List<ContactInformation> results = query.list();

		assertEquals(results.get(0).getLandline(), testLandline);
        assertEquals(results.get(0).getMobileNumber(), testMobileNum);
        assertEquals(results.get(0).getEmail(), testEmail);
		        
    }

    @Test
    public void test_update_contact_information_success() {
        //Add Person obj first
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);
       
        List<Person> result = personDao.selectPerson(1);
    

        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail,result.get(0));

        int testSelectedContactId = 1;
        String testUpdateLandline = "2000";
        String testUpdateMobileNum = "8-7000";
        String testUpdateEmail = "feedback@jfc.com.ph";

        contactInformationDao.updateContactInformation(
            testSelectedContactId, testUpdateLandline, testUpdateMobileNum,
            testUpdateEmail);

        List<ContactInformation> contact = contactInformationDao.selectContact(1);

        assertEquals(contact.get(0).getLandline(), testUpdateLandline);
        assertEquals(contact.get(0).getMobileNumber(), testUpdateMobileNum);
        assertEquals(contact.get(0).getEmail(), testUpdateEmail);
    }

    @Test
    public void test_update_contact_information_with_non_existent_contact_obj_fail() {
        //Add Person obj first
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);

        List<Person> result = personDao.selectPerson(1);
      
        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail,result.get(0));

        int testSelectedContactId = 2;
        String testUpdateLandline = "2000";
        String testUpdateMobileNum = "8-7000";
        String testUpdateEmail = "feedback@jfc.com.ph";

       contactInformationDao.updateContactInformation(
            testSelectedContactId, testUpdateLandline, testUpdateMobileNum,
            testUpdateEmail);

        List<ContactInformation> contact = contactInformationDao.selectContact(1);

        //Checks if values did not changed
        assertEquals(contact.get(0).getLandline(), testLandline);
        assertEquals(contact.get(0).getMobileNumber(), testMobileNum);
        assertEquals(contact.get(0).getEmail(), testEmail);
    }

    @Test
    public void test_delete_contact_information_success() {
        //Add Person obj first
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);

        //Add a contact obj 
        List<Person> result = personDao.selectPerson(1);
        contactInformationDao.addContactInformation(testLandline, testMobileNum, testEmail,result.get(0));
        int testSelectedContactId = 1;

        //Delete the obj
        boolean didDelete = contactInformationDao.deleteContact(testSelectedContactId);
        assertTrue(didDelete == true);
    }

    @Test
    public void test_delete_contact_information_with_nonexistent_id_fail() {
        //Add Person obj first
        personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
        testIsCurrentlyEmployed, testContactInformations, testRoles);

        int testSelectedContactId = 2;

        //Delete the obj
        boolean didDelete = contactInformationDao.deleteContact(testSelectedContactId);
        assertTrue(didDelete == false);
    }
}
