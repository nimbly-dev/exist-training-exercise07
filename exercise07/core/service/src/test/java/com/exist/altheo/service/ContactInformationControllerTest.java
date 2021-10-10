package com.exist.altheo.service;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;

import com.exist.altheo.dao.ContactInformationDao;
import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.model.ContactInformation;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class ContactInformationControllerTest extends TestCase {
    private ContactInformationController contactInformationController;
    private PersonDao personDao;
    private ContactInformationDao contactInformationDao;
    
    
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.contactInformationController = new ContactInformationController();
        this.personDao = new PersonDao();
        this.contactInformationDao = new ContactInformationDao();
        // System.setErr(new PrintStream(errContent));
        // this.person1 = new Person(1.5, "244", "John", "Doe", "Doo", 
        // "Jr", "The Third", "Manila", LocalDate.of(2020, 1, 30), false);
        // this.person2 = new Person(1.5, "244", "Sponge", "bob", "Squarepants", 
        // "", "", "Bikini Bottom", LocalDate.of(2020, 1, 30), true);
	}
  
    @Test 
    public void test_add_contact_information_controller_success(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try {
            contactInformationController.addContactInformation("8666", "7000", "spongebob@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_add_contact_information_controller_nonexistent_person_id_fail() {
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->contactInformationController.addContactInformation("111", "111", "111@gmail.com", 15));

        assertEquals(exception.getMessage(), "person id " + 15 + " does not exist");
    }

    @Test 
    public void test_add_contact_information_controller_with_blank_input_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

       
		ValidationException exception = assertThrows(ValidationException.class, 
        ()->contactInformationController.addContactInformation("", "", "", 1));

        assertEquals(exception.getMessage(), "Inputs must not be empty");
        
    }

    @Test 
    public void test_add_contact_information_controller_with_nonascii_input_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

       
		ValidationException exception = assertThrows(ValidationException.class, 
        ()->contactInformationController.addContactInformation("手田水口火竹口竹火竹", 
        "手田水口火竹口竹火竹", "手田水口火竹口竹火竹", 1));

        assertEquals(exception.getMessage(), "Inputs must be ascii characters");
    }

    @Test 
    public void test_update_contact_information_controller_success(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try {
            contactInformationController.addContactInformation("8666", "7000", "spongebob@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
  
        try {
            contactInformationController.updateContactInformation(
                "9999", "1111", "new@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        ContactInformation updatedContactInfo = contactInformationDao.selectContact(1);

        assertEquals(updatedContactInfo.getLandline(), "9999");
        assertEquals(updatedContactInfo.getMobileNumber(), "1111");
        assertEquals(updatedContactInfo.getEmail(), "new@gmail.com");
    }

    @Test 
    public void test_update_contact_information_controller_with_blank_input_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try {
            contactInformationController.addContactInformation("8666", "7000", "spongebob@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        ValidationException exception = assertThrows(ValidationException.class, 
        ()->contactInformationController.updateContactInformation("", "", "", 1));

        assertEquals(exception.getMessage(), "Inputs must not be empty");
    }

    @Test 
    public void test_update_contact_information_controller_with_nonascii_input_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try {
            contactInformationController.addContactInformation("8666", "7000", "spongebob@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        ValidationException exception = assertThrows(ValidationException.class, 
        ()->contactInformationController.updateContactInformation("手田水口火竹口竹火竹", 
        "手田水口火竹口竹火竹", "手田水口火竹口竹火竹", 1));

        assertEquals(exception.getMessage(), "Inputs must be ascii characters");
    }

    @Test //TODO - FIX BUG, STUCK ON EXECUTING TEST CASE UPON BULK TEST EXECUTION
    public void test_update_contact_information_controller_with_nonexistent_person_id_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try {
            contactInformationController.addContactInformation("8666", "7000", "spongebob@gmail.com", 1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->contactInformationController.updateContactInformation("111", 
        "222", "111@gmail.com", 10));

        assertEquals(exception.getMessage(), "Person id " + 10 + " does not exist");
    }

    @Test 
    public void test_delete_contact_information_controller_success(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        try{
            contactInformationController.deleteContactInformation(1);
        }catch(NoResultException nre){
            System.out.println(nre.getMessage());
        }

    }

    @Test 
    public void test_delete_contact_information_controller_with_nonexistent_role_id_fail(){
        personDao.addPerson("Bikini Bottom", 1.5, "244", LocalDate.of(2020, 1, 30), true, 
        "Sponge", "bob", "Squarepants", "", ""); //ID OF 1

        NoResultException exception = assertThrows(NoResultException.class, 
        ()->contactInformationController.deleteContactInformation(1));

        assertEquals(exception.getMessage(), "person id " + 1 + " does not exist");
    }
}
