package com.exist.altheo;

import com.exist.altheo.service.ContactInformationController;
import com.exist.altheo.service.PersonController;
import com.exist.altheo.service.RoleController;
import com.exist.altheo.utility.Reader;
import com.exist.altheo.view.Display;

public class ServiceController {
    private PersonController personController;
    private ContactInformationController contactInformationController;
    private RoleController roleController;

    public ServiceController(){
        this.personController = new PersonController();
        this.contactInformationController = new ContactInformationController();
        this.roleController = new RoleController();
    }

    public void serviceUserInterface() {
        String choice = Reader.readString("Enter command");
        boolean isEndServiceUserInterface = false;

        Display.displayServiceUserInterfaceCommands();
        
        do {
            switch (choice.toUpperCase()) {
                case "P":
                    personController.personInterface();
                    break;
                case "R":
                    roleController.roleUserInterface();
                    break;
                case "C":
                    contactInformationController.contactInformationInterface();
                    break;
                case "V":
                    Display.displayServiceUserInterfaceCommands();
                    break;
                case "X":
                    isEndServiceUserInterface = true;
                    break;
                default:
                    System.out.println("Wrong input, please only input commands shown");
                    Display.displayServiceUserInterfaceCommands();
                    break;
            }
        } while (isEndServiceUserInterface == false);
    }
}
