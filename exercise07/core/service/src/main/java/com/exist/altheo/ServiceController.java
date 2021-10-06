package com.exist.altheo;

import com.exist.altheo.service.ContactInformationController;
import com.exist.altheo.service.PersonController;
import com.exist.altheo.service.RoleController;

public class ServiceController {
    private PersonController personController;
    private ContactInformationController contactInformationController;
    private RoleController roleController;

    public ServiceController(){
        this.personController = new PersonController();
        this.contactInformationController = new ContactInformationController();
        this.roleController = new RoleController();

        //TODO - CREATE CONSOLE-UI FOR THE MAIN APP HERE
    }
}
