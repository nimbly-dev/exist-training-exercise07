package com.exist.altheo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Person implements Serializable{
    int personId;
    double gwa;

    String zipCode;

    String name;
    //TODO - CHANGE TO ENUM
    String address;

    Date dateHired;
    boolean isCurrentlyEmployed;
    Set<ContactInformation> contactInformations = new HashSet<ContactInformation>(0);
    List<Role> roles;

    public Person(){

    }

    public Person(double gwa, String zipCode, String name, String address, Date dateHired,
            boolean isCurrentlyEmployed) {
        this.gwa = gwa;
        this.zipCode = zipCode;
        this.name = name;
        this.address = address;
        this.dateHired = dateHired;
        this.isCurrentlyEmployed = isCurrentlyEmployed;
    }

    public Person(double gwa, String zipCode, String name, String address, Date dateHired, boolean isCurrentlyEmployed,
            Set<ContactInformation> contactInformations, List<Role> roles) {
        this.gwa = gwa;
        this.zipCode = zipCode;
        this.name = name;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<ContactInformation> getContactInformations() {
        return contactInformations;
    }

    public void setContactInformations(Set<ContactInformation> contactInformations) {
        this.contactInformations = contactInformations;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
   
    
}
