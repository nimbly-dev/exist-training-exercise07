package com.exist.altheo.service;

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
    String inputEmail){
        if(StringUtils.isEmpty(inputLandline) ||  StringUtils.isEmpty(inputMobileNumber)
            || StringUtils.isEmpty(inputEmail)){
            System.out.println("Inputs must not be empty");
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail))
        {
            System.out.println("Input is invalid");
        }
        else{
            contactInformationDao.addContactInformation(inputLandline, 
            inputMobileNumber, inputEmail);
        }
    }

    //TODO - IF UPDATE FAIL, PRINT SAYING UPDATE FAILED
    public void updateContactInformation(String inputLandline, String inputMobileNumber, 
    String inputEmail){
        if(StringUtils.isEmpty(inputLandline) ||  StringUtils.isEmpty(inputMobileNumber)
        || StringUtils.isEmpty(inputEmail)){
        System.out.println("Inputs must not be empty");
        }
        else if(!StringUtils.isAsciiPrintable(inputLandline) || 
                !StringUtils.isAsciiPrintable(inputMobileNumber) ||
                !StringUtils.isAsciiPrintable(inputEmail))
        {
            System.out.println("Input is invalid");
        }
        else{
            contactInformationDao.addContactInformation(inputLandline, 
            inputMobileNumber, inputEmail);
        }
    }
    
    //TODO - IF DELETE FAIL, PRINT SAYING DELETE FAILED
    public void deleteContactInformation(){
        int selectedIndex = Reader.readInt("Enter roleId you wish to delete ");
        contactInformationDao.deleteContact(selectedIndex);
    }

    //Contact information UI
    public void contactInformationInterface(){
        System.out.println("You are now in the Contact Information Functionality Interface");
        boolean isEndRoleUserInterface = false;
        
        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "A":
                    String inputLandline = Reader.readString("Enter landline");
                    String inputMobileNumber = Reader.readString("Enter landline");
                    String inputEmail = Reader.readString("Enter landline");
                    addContactInformation(inputLandline, inputMobileNumber, inputEmail);
                    break;
                case "D":
                    deleteContactInformation();
                    break;
                case "U":
                    String updateInputLandline = Reader.readString("Enter landline");
                    String updateInputMobileNumber = Reader.readString("Enter landline");
                    String updateInputEmail = Reader.readString("Enter landline");
                    updateContactInformation(updateInputLandline, updateInputMobileNumber, 
                    updateInputEmail);
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
