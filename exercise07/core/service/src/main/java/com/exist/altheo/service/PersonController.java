package com.exist.altheo.service;

import java.util.Date;

import javax.persistence.NoResultException;

import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.dao.RoleDao;
import com.exist.altheo.utility.Reader;

import org.apache.commons.lang3.StringUtils;

public class PersonController {

    private PersonDao personDao;
    private RoleDao roleDao;

    public PersonController(){
        this.personDao = new PersonDao();
        this.roleDao = new RoleDao();
    }
    
    public void addPerson(
        String inputFirstName, String inputMiddleName, String inputLastName, 
        String inputSuffix, String inputTitle, double inputGwa, String inputAddress, 
        String inputZipCode, Date inputDateHired, boolean inputisCurrentlyEmployed
    ){
        if(StringUtils.isBlank(inputFirstName) || StringUtils.isBlank(inputMiddleName) 
           || StringUtils.isBlank(inputLastName) || StringUtils.isBlank(inputSuffix) 
           || StringUtils.isBlank(inputTitle) || StringUtils.isBlank(inputZipCode)
           || StringUtils.isBlank(inputAddress))
        {
            System.out.println("Input is blank, please fill out the fields");
        }
        else if(!StringUtils.isAsciiPrintable(inputFirstName) || !StringUtils.isAsciiPrintable(inputMiddleName) 
             || !StringUtils.isAsciiPrintable(inputLastName) || !StringUtils.isAsciiPrintable(inputSuffix) 
             || !StringUtils.isAsciiPrintable(inputTitle) || !StringUtils.isAsciiPrintable(inputZipCode)
             || !StringUtils.isAsciiPrintable(inputAddress)){
            System.out.println("Input characters are not supported ");
        }
        else{
            personDao.addPerson(inputAddress, inputGwa, inputZipCode, inputDateHired, inputisCurrentlyEmployed, 
            inputFirstName, inputMiddleName, inputLastName, inputSuffix, inputTitle);
            System.out.println("Person succesfully added");
        }
    }

    public void updatePerson(
        String inputFirstName, String inputMiddleName, String inputLastName, 
        String inputSuffix, String inputTitle, double inputGwa, String inputAddress, 
        String inputZipCode, Date inputDateHired, boolean inputisCurrentlyEmployed,
        int selectedPersonId
    ){
        if(StringUtils.isBlank(inputFirstName) || StringUtils.isBlank(inputMiddleName) 
        || StringUtils.isBlank(inputLastName) || StringUtils.isBlank(inputSuffix) 
        || StringUtils.isBlank(inputTitle) || StringUtils.isBlank(inputZipCode)
        || StringUtils.isBlank(inputAddress)){
            System.out.println("Input is blank, please fill out the fields");
        }
        else if(!StringUtils.isAsciiPrintable(inputFirstName) || !StringUtils.isAsciiPrintable(inputMiddleName) 
        || !StringUtils.isAsciiPrintable(inputLastName) || !StringUtils.isAsciiPrintable(inputSuffix) 
        || !StringUtils.isAsciiPrintable(inputTitle) || !StringUtils.isAsciiPrintable(inputZipCode)
        || !StringUtils.isAsciiPrintable(inputAddress)){
            System.out.println("Input characters are not supported ");
        }
        else{
            try{
                personDao.updatePerson(inputAddress, inputGwa, inputZipCode, inputDateHired, inputisCurrentlyEmployed, 
                inputFirstName, inputMiddleName, inputLastName, inputSuffix, inputTitle, selectedPersonId);
            }catch(NoResultException nre){
                System.out.println(nre.getMessage());
            }
            System.out.println("Person updated added");
        }
    }
    
    public void deletePerson(int selectedPersonId){
        try{
            personDao.selectPerson(selectedPersonId);
        }catch(NoResultException nre){
           System.out.println(nre.getMessage());
        } 
    }

    public void assignRoleToPerson(String roleName, int selectedPersonId){
        if(StringUtils.isBlank(roleName)){
            System.out.println("Input is blank, please fill out the fields");
        }
        else if(!StringUtils.isAsciiPrintable(roleName)){
            System.out.println("Input characters are not supported ");
        }else{
            try{
                roleDao.assignRoleToPerson(selectedPersonId, roleName);
            }catch(NoResultException nre){
                System.out.println(nre.getMessage());
            }
        }
    }

    public void listAllPerson(){
        System.out.println("You are on Person List Functionality Interface");
        boolean isEndListAllPersonInterface = false;

        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "BY_GWA":
                    break;
                case "BY_DATE_HIRED":
                    break;
                case "BY_LASTNAME":
                    break;
                default:
                    break;
            }
        } while (isEndListAllPersonInterface == false);
    }

    //TODO
    public void personInterface(){
        System.out.println("You are now in the Person Functionality Interface");
        boolean isEndPersonUserInterface = false;

        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "A":
                    break;
                case "U":
                    break;
                case "D":
                    break;
                case "L":
                    break;
                case "ADD_ROLE":
                    break;
                case "ADD_CONTACT":
                    break;
                default:
                    break;
            }
        } while (isEndPersonUserInterface == false);
    }

    //Utility or Helper methods
    //TODO
    public void printPersonsByDateHired(){

    }

    //TODO
    public void printPersonsByGwa(){

    }

    //TODO
    public void printPersonsByLastName(){

    }
}
