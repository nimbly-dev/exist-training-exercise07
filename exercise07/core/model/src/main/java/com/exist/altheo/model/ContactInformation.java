package com.exist.altheo.model;

public class ContactInformation {
    int contactId;
    String landline;
    String mobileNumber;
    String email;

    Person person;

    public ContactInformation(){ }

    public ContactInformation(String landline, String mobileNumber, String email) {
        this.landline = landline;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public ContactInformation(String landline, String mobileNumber, String email, Person person) {
        this.landline = landline;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.person = person;
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

    
}
