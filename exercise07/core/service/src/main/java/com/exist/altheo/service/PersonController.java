package com.exist.altheo.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import com.exist.altheo.dao.ContactInformationDao;
import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.dao.RoleDao;
import com.exist.altheo.helper.SortPersonByDateHired;
import com.exist.altheo.helper.SortPersonByGwa;
import com.exist.altheo.helper.SortPersonByLastName;
import com.exist.altheo.model.Person;
import com.exist.altheo.utility.Reader;
import com.exist.altheo.view.Display;

import org.apache.commons.lang3.StringUtils;


public class PersonController {

    private PersonDao personDao;
    private ContactInformationDao contactInformationDao;
    private RoleDao roleDao;

    public PersonController(){
        this.personDao = new PersonDao();
        this.roleDao = new RoleDao();
        this.contactInformationDao = new ContactInformationDao();
    }
    
    public void addPerson(
        String inputFirstName, String inputMiddleName, String inputLastName, 
        String inputSuffix, String inputTitle, double inputGwa, String inputAddress, 
        String inputZipCode, LocalDate inputDateHired, boolean inputisCurrentlyEmployed
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
        String inputZipCode, LocalDate inputDateHired, boolean inputisCurrentlyEmployed,
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

    public void assignContactInfoToPerson(
        String inputLandline, String inputMobileNumber, String inputEmail,
        int personSelectId
    ){
        if(StringUtils.isBlank(inputLandline) || StringUtils.isBlank(inputMobileNumber)
          || StringUtils.isBlank(inputEmail)){
            System.out.println("Input is blank, please fill out the fields");
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail)){
            System.out.println("Input characters are not supported ");
        }
        else{
            try{
                contactInformationDao.addContactInformation(inputLandline, inputMobileNumber, 
                inputEmail, personSelectId);
            }catch(NoResultException nre){
                System.out.println(nre.getMessage());
            }
        }
    }

    public void listAllPerson(){
        System.out.println("You are on Person List Functionality Interface");
        boolean isEndListAllPersonInterface = false;

        Display.displayListPersonInterfaceCommands();
        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "BY_GWA":
                    printPersonsByGwa();
                    break;
                case "BY_DATE_HIRED":
                    printPersonsByDateHired();
                    break;
                case "BY_LASTNAME":
                    printPersonsByLastName();
                    break;
                case "V":
                    Display.displayListPersonInterfaceCommands();
                    break;
                case "X":
                    isEndListAllPersonInterface = true;
                    break;
                default:
                    break;
            }
        } while (isEndListAllPersonInterface == false);
    }

    public void personInterface(){
        System.out.println("You are now in the Person Functionality Interface");
        boolean isEndPersonUserInterface = false;

        Display.displayPersonInterfaceCommands();
        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "A":
                    String inputFirstName = Reader.readString("Enter first name");
                    String inputMiddleName = Reader.readString("Enter middle name");
                    String inputLastName = Reader.readString("Enter last name");
                    String inputSuffix = Reader.readString("Enter suffix name");
                    String inputTitle = Reader.readString("Enter title name");
                    double inputGwa = Reader.readDouble("Enter gwa");
                    String inputAddress = Reader.readString("Enter address");
                    String inputZipCode = Reader.readString("Enter zipcode");
                    boolean inputisCurrentlyEmployed = Reader.readBoolean("Currently Employed");
                    LocalDate inputDateHired = Reader.readLocalDate("Enter date hired ");

                    addPerson(inputFirstName, inputMiddleName, inputLastName, inputSuffix, inputTitle, 
                    inputGwa, inputAddress, inputZipCode, inputDateHired, inputisCurrentlyEmployed);
                    break;
                case "U":
                    String updateInputFirstName = Reader.readString("Enter first name");
                    String updateInputMiddleName = Reader.readString("Enter middle name");
                    String updateInputLastName = Reader.readString("Enter last name");
                    String updateInputSuffix = Reader.readString("Enter suffix name");
                    String updateInputTitle = Reader.readString("Enter title name");
                    double updateInputGwa = Reader.readDouble("Enter gwa");
                    String updateInputAddress = Reader.readString("Enter address");
                    String updateInputZipCode = Reader.readString("Enter zipcode");
                    boolean updateInputisCurrentlyEmployed = Reader.readBoolean("Currently Employed");
                    LocalDate updateInputDateHired = Reader.readLocalDate("Enter date hired ");

                    int updateSelectedPersonId = Reader.readInt("Enter selected person id ");

                    updatePerson(updateInputFirstName, updateInputMiddleName, updateInputLastName, 
                    updateInputSuffix, updateInputTitle, updateInputGwa, updateInputAddress, 
                    updateInputZipCode, updateInputDateHired, updateInputisCurrentlyEmployed, 
                    updateSelectedPersonId);

                    break;
                case "D":
                    int deleteSelectedPersonId = 
                        Reader.readInt("Enter person id of the person data that you wish to delete");
                    deletePerson(deleteSelectedPersonId);
                    break;
                case "L":
                    listAllPerson();
                    break;
                case "ADD_ROLE":
                    String roleName = Reader.readString("Enter new rolename ");
                    int selectedPersonIdForRole = Reader.readInt("Enter selected person id");
                    assignRoleToPerson(roleName, selectedPersonIdForRole);
                    break;
                case "ADD_CONTACT":
                    String inputLandline = Reader.readString("Enter landline ");
                    String inputMobileNumber = Reader.readString("Enter landline ");
                    String inputEmail = Reader.readString("Enter landline ");
                    int selectedPersonIdForContact = Reader.readInt("Enter selected person id");
                    assignContactInfoToPerson(inputLandline, inputMobileNumber, 
                    inputEmail, selectedPersonIdForContact);
                    break;
                case "V":
                    Display.displayPersonInterfaceCommands();
                    break;
                case "X":
                    isEndPersonUserInterface = true;
                    break;
                default:
                    break;
            }
        } while (isEndPersonUserInterface == false);
    }

    //Utility or Helper methods
    public void printPersonsByDateHired(){
        List<Person> persons = personDao.getListPerson();

        Collections.sort(persons, new SortPersonByDateHired());

        printPerson(persons);
    }

    public void printPersonsByGwa(){
        List<Person> persons = personDao.getListPerson();

        Collections.sort(persons, new SortPersonByGwa());

        printPerson(persons);
    }

    public void printPersonsByLastName(){
        List<Person> persons = personDao.getListPerson();

        Collections.sort(persons, new SortPersonByLastName());

        printPerson(persons);
    }

    public void printPerson(List<Person> persons){

        persons.stream().forEach((p)->{
            System.out.println("=============================================");
            String fullName = p.getFirstName()+" "+p.getMiddleName()+ " " + p.getLastName()
            +p.getSuffix() + " " + p.getTitle();
            System.out.println("Name: "+fullName);
            System.out.println("Person ID: " +p.getPersonId());
            System.out.println("Address: "+p.getAddress());
            System.out.println("Zipcode: "+p.getZipCode());
            System.out.println("GWA: "+p.getGwa());
            System.out.println("Currently Employed: "+p.getIsCurrentlyEmployed());
            System.out.println("Date Hired: "+p.getDateHired());
        });
    }
}
