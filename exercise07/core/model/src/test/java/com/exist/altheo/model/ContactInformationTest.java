package com.exist.altheo.model;

import java.time.LocalDate;
import java.time.ZoneId;

import com.exist.altheo.model.ContactInformation.Contact;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class ContactInformationTest extends TestCase {
    // private Person person1;
    private Person person2;
    private Person jolibee;
    
    private LocalDate dateToday;
    private LocalDate exampleDate;
    
	@Override
	@BeforeEach
	protected void setUp() throws Exception {
        ZoneId.systemDefault();
        this.dateToday = LocalDate.now();
        this.exampleDate = LocalDate.of(2012, 1, 1);

        // Set<ContactInformation> testContact = new HashSet<ContactInformation>();
		// this.person1 = new Person(1.25, "322", "John", "Doe", "Doo", "Jr.", "The Third", 
        // "Doobi Manila", dateToday, true);

        this.person2 = new Person(5, "322", "Scooby", "Doobo", "Doo", "Sr.", 
        "Dog", "Makati", dateToday, exampleDate, true);
        
        this.jolibee =  new Person(3, "52", "Bida", "Ang", "Saya", "Bumblee", "Bee", 
        "Brasil Brazil", exampleDate, dateToday,true);
	}

    @Test
    public void test_contact_information_getters() {
        ContactInformation contactInformation1 = new ContactInformation(
            "1111", "2222-3333", "hotdigitydog@gmail.com");
        contactInformation1.setPerson(person2);

        assertTrue(contactInformation1.getLandline() == "1111");
        assertTrue(contactInformation1.getMobileNumber() == "2222-3333");
        assertTrue(contactInformation1.getEmail() == "hotdigitydog@gmail.com");
        assertTrue(contactInformation1.getPerson().getFirstName() == "Scooby");
    }

    @Test 
    public void test_contact_information_setters(){
        ContactInformation contactInformation = new ContactInformation(
            "4444", "5555", "feedback@jfc.com.ph");

        contactInformation.setLandline("9999");
        contactInformation.setMobileNumber("8-7000");
        contactInformation.setEmail("writeus@ph.mcd.com");
        contactInformation.setPerson(jolibee);

        assertTrue(contactInformation.getLandline() == "9999");
        assertTrue(contactInformation.getMobileNumber() == "8-7000");
        assertTrue(contactInformation.getEmail() == "writeus@ph.mcd.com");
        assertTrue(contactInformation.getPerson().getFirstName()== "Bida");
    }

    @Test
    public void test_enum(){
        ContactInformation contactInformation = new ContactInformation(
            "4444", "5555", "feedback@jfc.com.ph");

        contactInformation.getAddressMap().put(Contact.EMAIL, "feedback@jfc.com.ph");

        System.out.println(contactInformation.getAddressMap().get(Contact.EMAIL));
        
    }
}
