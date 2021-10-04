package com.exist.altheo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person {
    int personId;
    String firstName;
    String lastName;
    String middleName;
    String suffix;
    String title;

    double gwa;

    String streetNum;
    String barangay;
    String city;
    String zipCode;

    Date dateHired;
    boolean isCurrentlyEmployed;
    Set<ContactInformation> contactInformations = new HashSet<ContactInformation>(0);
    List<Role> roles ;

    public Person(){

    }
    
    public Person(String firstName, String lastName, String middleName, String suffix, String title, double gwa,
            String streetNum, String barangay, String city, String zipCode, Date dateHired, boolean isCurrentlyEmployed,
            Set<ContactInformation> contactInformations, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.suffix = suffix;
        this.title = title;
        this.gwa = gwa;
        this.streetNum = streetNum;
        this.barangay = barangay;
        this.city = city;
        this.zipCode = zipCode;
        this.dateHired = dateHired;
        this.isCurrentlyEmployed = isCurrentlyEmployed;
        this.contactInformations = contactInformations;
        this.roles = roles;
    }




    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    public boolean getIsCurrentlyEmployed() {
        return isCurrentlyEmployed;
    }

    public void setIsCurrentlyEmployed(boolean isCurrentlyEmployed) {
        this.isCurrentlyEmployed = isCurrentlyEmployed;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<ContactInformation> getContactInformations() {
        return contactInformations;
    }

    public void setContactInformations(Set<ContactInformation> contactInformations) {
        this.contactInformations = contactInformations;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getGwa() {
        return gwa;
    }

    public void setGwa(double gwa) {
        this.gwa = gwa;
    }


    public String getZipCode() {
        return zipCode;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    
}
