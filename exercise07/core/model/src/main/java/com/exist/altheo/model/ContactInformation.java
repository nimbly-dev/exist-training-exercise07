package com.exist.altheo.model;

import java.util.EnumMap;

public class ContactInformation {
    int contactId;
    int personId;
    String landline;
    String mobileNumber;
    String email;

    //TODO- APPLY ENUM TO DB
    enum Contact{
        LANDLINE, MOBILE_NUMBER, EMAIL
    }

    EnumMap<Contact, String> addressMap = new EnumMap<>(Contact.class);

    Person person;

    public ContactInformation(){ 
        
    }

    public ContactInformation(String landline, String mobileNumber, String email) {
        this.landline = landline;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }


    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    public EnumMap<Contact, String> getAddressMap() {
        return addressMap;
    }


    public void setAddressMap(EnumMap<Contact, String> addressMap) {
        this.addressMap = addressMap;
    }
    
}
