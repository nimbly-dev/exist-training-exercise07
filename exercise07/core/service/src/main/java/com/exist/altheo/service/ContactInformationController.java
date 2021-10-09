package com.exist.altheo.service;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;

import com.exist.altheo.dao.ContactInformationDao;
import com.exist.altheo.utility.Reader;
import com.exist.altheo.view.Display;

import org.apache.commons.lang3.StringUtils;

public class ContactInformationController {
    private ContactInformationDao contactInformationDao;

    public ContactInformationController(){
        this.contactInformationDao = new ContactInformationDao();
    }
    
    public void addContactInformation(String inputLandline, String inputMobileNumber, 
    String inputEmail, int selectedPersonId) throws ValidationException, NoResultException
    {
        if(StringUtils.isEmpty(inputLandline) ||  StringUtils.isEmpty(inputMobileNumber)
            || StringUtils.isEmpty(inputEmail)){
            throw new ValidationException("Inputs must not be empty");
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail))
        {
            throw new ValidationException("Inputs must be ascii characters");
        }
        else{
            contactInformationDao.addContactInformation(inputLandline, 
            inputMobileNumber, inputEmail,selectedPersonId);
        }
    }

    public void updateContactInformation(String inputLandline, String inputMobileNumber, 
    String inputEmail, int updateSelectedContactId) throws ValidationException, NoResultException{
        if(StringUtils.isEmpty(inputLandline) ||  StringUtils.isEmpty(inputMobileNumber)
        || StringUtils.isEmpty(inputEmail)){
             throw new ValidationException("Inputs must not be empty");
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail))
        {
            throw new ValidationException("Inputs must be ascii characters");
        }
        else{
            contactInformationDao.updateContactInformation(updateSelectedContactId, inputLandline, 
            inputMobileNumber, inputEmail);
        }
    }
    
    public void deleteContactInformation(int selectedIndex) throws NoResultException{
        contactInformationDao.deleteContact(selectedIndex);
    }

    //Contact information UI
    public void contactInformationInterface(){
        System.out.println("You are now in the Contact Information Functionality Interface");
        boolean isEndRoleUserInterface = false;
        
        
        do {
            String command = Reader.readString("Enter command: ");
            switch (command.toUpperCase()) {
                case "A":
                    String inputLandline = Reader.readString("Enter landline");
                    String inputMobileNumber = Reader.readString("Enter landline");
                    String inputEmail = Reader.readString("Enter landline");
                    int inputSelectedPersonId = Reader.readInt("Enter selected person id ");
                    try {
                        addContactInformation(inputLandline, inputMobileNumber, 
                        inputEmail,inputSelectedPersonId);
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (NoResultException nre){
                        System.out.println(nre.getMessage());
                    }
                    break;
                case "D":
                    try{
                        int selectedIndex = Reader.readInt("Enter roleId you wish to delete ");
                        deleteContactInformation(selectedIndex);
                    }catch(NoResultException nre){
                        System.out.println(nre.getMessage());
                    }
                    break;
                case "U":
                    String updateInputLandline = Reader.readString("Enter landline");
                    String updateInputMobileNumber = Reader.readString("Enter landline");
                    String updateInputEmail = Reader.readString("Enter landline");
                    int updateSelectedContactId = Reader.readInt("Enter selected person id ");
                    try {
                        updateContactInformation(updateInputLandline, updateInputMobileNumber, 
                        updateInputEmail, updateSelectedContactId);
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    } catch (NoResultException nre){
                        System.out.println(nre.getMessage());
                    }
                    break;
                case "V":
                    Display.displayContactInformationInterfaceCommands();
                    break;
                default:
                    System.out.println("Wrong input, please put a valid input");
                    break;
            }
        } while (isEndRoleUserInterface);
    }

}
