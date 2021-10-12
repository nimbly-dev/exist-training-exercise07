package com.exist.altheo.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.xml.bind.ValidationException;

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
    
    public void addPersonController(
        String inputFirstName, String inputMiddleName, String inputLastName, 
        String inputSuffix, String inputTitle, double inputGwa, String inputAddress, 
        String inputZipCode, LocalDate inputDateHired, LocalDate inputBirthDay,
        boolean inputisCurrentlyEmployed
    ) throws ValidationException {
        if(StringUtils.isBlank(inputFirstName) || StringUtils.isBlank(inputLastName)  
           || StringUtils.isBlank(inputZipCode) || StringUtils.isBlank(inputAddress))
        {
            throw new ValidationException("Input is blank, please fill out the fields"); 
        }
        else if(!StringUtils.isAsciiPrintable(inputFirstName) || !StringUtils.isAsciiPrintable(inputMiddleName) 
             || !StringUtils.isAsciiPrintable(inputLastName) || !StringUtils.isAsciiPrintable(inputSuffix) 
             || !StringUtils.isAsciiPrintable(inputTitle) || !StringUtils.isAsciiPrintable(inputZipCode)
             || !StringUtils.isAsciiPrintable(inputAddress)){
            throw new ValidationException("Input characters are not supported "); 
        }
        else{
            personDao.addPerson(inputAddress, inputGwa, inputZipCode, inputDateHired,inputBirthDay
            ,inputisCurrentlyEmployed,  inputFirstName, inputMiddleName, inputLastName, inputSuffix,
            inputTitle);
            System.out.println("Person succesfully added");
        }
    }

    public void updatePersonController(
        String inputFirstName, String inputMiddleName, String inputLastName, 
        String inputSuffix, String inputTitle, double inputGwa, String inputAddress, 
        String inputZipCode, LocalDate inputDateHired,  LocalDate inputBirthdate,boolean inputisCurrentlyEmployed,
        int selectedPersonId
    ) throws ValidationException , NoResultException
    {
        if(StringUtils.isBlank(inputFirstName) || StringUtils.isBlank(inputLastName) 
        || StringUtils.isBlank(inputZipCode)
        || StringUtils.isBlank(inputAddress)){
            throw new ValidationException("Input is blank, please fill out the fields"); 
        }
        else if(!StringUtils.isAsciiPrintable(inputFirstName) || !StringUtils.isAsciiPrintable(inputMiddleName) 
        || !StringUtils.isAsciiPrintable(inputLastName) || !StringUtils.isAsciiPrintable(inputSuffix) 
        || !StringUtils.isAsciiPrintable(inputTitle) || !StringUtils.isAsciiPrintable(inputZipCode)
        || !StringUtils.isAsciiPrintable(inputAddress)){
            throw new ValidationException("Input characters are not supported "); 
        }
        else{
            
            personDao.updatePerson(inputAddress, inputGwa, inputZipCode, inputDateHired,inputBirthdate ,
            inputisCurrentlyEmployed, 
            inputFirstName, inputMiddleName, inputLastName, inputSuffix, inputTitle, selectedPersonId);
           
            System.out.println("Person updated added");
        }
    }
    
    public void deletePersonController(int selectedPersonId) throws NoResultException{
         personDao.deletePerson(selectedPersonId);
    }

    public void assignRoleToPersonController(String roleName, int selectedPersonId)
    throws ValidationException, PersistenceException{ //MODIFY ERROR MSSG OF PERSISTENCE EXCEPTION
        if(StringUtils.isBlank(roleName)){
            throw new ValidationException("Input is blank, please fill out the fields"); 
        }
        else if(!StringUtils.isAsciiPrintable(roleName)){
            throw new ValidationException("Input characters are not supported "); 
        }else{
            roleDao.addRoleAndAssignToPerson(roleName, selectedPersonId);
            System.out.println("Successfuly added role to person");
        }
    }

    public void assignContactInfoToPersonController(
        String inputLandline, String inputMobileNumber, String inputEmail,
        int personSelectId
    ) throws ValidationException, NoResultException{
        if(StringUtils.isBlank(inputLandline) || StringUtils.isBlank(inputMobileNumber)
          || StringUtils.isBlank(inputEmail)){
            throw new ValidationException("Input is blank, please fill out the fields"); 
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail)){
            throw new ValidationException("Input characters are not supported "); 
        }
        else{
            contactInformationDao.addContactInformation(inputLandline, inputMobileNumber, 
            inputEmail, personSelectId);
            System.out.println("Successfuly added contact to person");
        }
    }

    public void listAllPersonInterface(){
        System.out.println("You are on Person List Functionality Interface");
        boolean isEndListAllPersonInterface = false;

        Display.displayListPersonInterfaceCommands();

        do {
            String command = Reader.readString("Enter command: ");
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

        do {
            String command = Reader.readString("Enter command: ");
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
                    System.out.println("Enter what date that you are hired: ");
                    LocalDate inputDateHired = Reader.readLocalDate("Enter date hired ");
                    LocalDate inputBirthDate = Reader.readLocalDate("Enter birthday ");

                    try {
                        addPersonController(inputFirstName, inputMiddleName, inputLastName, inputSuffix, inputTitle, 
                        inputGwa, inputAddress, inputZipCode, inputDateHired,inputBirthDate ,inputisCurrentlyEmployed);
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
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
                    System.out.println("Enter what date that you are hired: ");
                    LocalDate updateInputDateHired = Reader.readLocalDate("Enter date hired ");
                    LocalDate updateInputBirthDate = Reader.readLocalDate("Enter birthday ");

                    int updateSelectedPersonId = Reader.readInt("Enter selected person id ");

                    try {
                        updatePersonController(updateInputFirstName, updateInputMiddleName, updateInputLastName, 
                        updateInputSuffix, updateInputTitle, updateInputGwa, updateInputAddress, 
                        updateInputZipCode, updateInputDateHired,updateInputBirthDate,
                        updateInputisCurrentlyEmployed, updateSelectedPersonId);
                    } catch (NoResultException | ValidationException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "D":
                    int deleteSelectedPersonId = 
                    Reader.readInt("Enter person id of the person data that you wish to delete");
                    try {
                        deletePersonController(deleteSelectedPersonId);
                    } catch (NoResultException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "L":
                    listAllPersonInterface();
                    break;
                case "ADD_ROLE":
                    String roleName = Reader.readString("Enter new rolename ");
                    int selectedPersonIdForRole = Reader.readInt("Enter selected person id");
                    try {
                        assignRoleToPersonController(roleName, selectedPersonIdForRole);
                    } catch (PersistenceException | ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "ADD_CONTACT":
                    String inputLandline = Reader.readString("Enter landline ");
                    String inputMobileNumber = Reader.readString("Enter landline ");
                    String inputEmail = Reader.readString("Enter landline ");
                    int selectedPersonIdForContact = Reader.readInt("Enter selected person id");
                    try {
                        assignContactInfoToPersonController(inputLandline, inputMobileNumber, 
                        inputEmail, selectedPersonIdForContact);
                    } catch (NoResultException | ValidationException e) {
                       System.out.println(e.getMessage());
                    }
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
            System.out.println("Roles: ");
            p.getRoles().stream().forEach((r)->{
                System.out.println("==========");
                System.out.println("Role id: " + r.getRoleId());
                System.out.println("Role name: " + r.getRoleName());
            });
            System.out.println("Contact: ");
            p.getContactInformations().stream().forEach((c)->{
                System.out.println("==========");
                System.out.println("Contact id: " + c.getContactId());
                System.out.println("Landline: " + c.getLandline());
                System.out.println("Mobile Number: " + c.getMobileNumber());
                System.out.println("Contact id: " + c.getContactId());
            });
        });
    }
}
