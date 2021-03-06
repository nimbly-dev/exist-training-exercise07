package com.exist.altheo.view;

public class Display {
    public static void displayRoleInterfaceCommands(){
        System.out.println("Below are the Role Interface Functionality commands ");
        System.out.println("[A] add a new role");
        System.out.println("[D] delete a specified role ");
        System.out.println("[U] update a specified role  ");
        System.out.println("[L] list all the role ");
        System.out.println("[X] exit the Role Interface ");
    }

    public static void displayContactInformationInterfaceCommands(){
        System.out.println("Below are the Contact Information Interface Functionality commands ");
        System.out.println("[A] add a new contact information");
        System.out.println("[D] delete a specified contact information ");
        System.out.println("[U] update a specified contact information  ");
        System.out.println("[X] exit the Contact Information Interface ");
    }

    public static void displayPersonInterfaceCommands() {
        System.out.println("Below are the Person Interface Functionality commands ");
        System.out.println("[A] add a new person information");
        System.out.println("[D] delete a specified person information ");
        System.out.println("[U] update a specified person information  ");
        System.out.println("[L] list all persons  ");
        System.out.println("[ADD_ROLE] assign a role to specified person information  ");
        System.out.println("[ADD_CONTACT] assign a contact to specified person information  ");
        System.out.println("[V] view the commands ");
        System.out.println("[X] exit the Contact Information Interface ");
    }

    public static void displayListPersonInterfaceCommands(){
        System.out.println("Below are the List Persons Interface Functionality commands ");
        System.out.println("[BY_GWA] - print persons by gwa");
        System.out.println("[BY_DATE_HIRED] - print persons by gwa");
        System.out.println("[BY_LASTNAME] - print persons by gwa");
        System.out.println("[V] - view the commands");
        System.out.println("[X] - exit the List Person Interface");
    }

    public static void displayServiceUserInterfaceCommands(){
        System.out.println("Below are the Service Interface Functionality commands ");
        System.out.println("[P] - open person interface");
        System.out.println("[R] - open role interface");
        System.out.println("[C] - open contact information interface");
        System.out.println("[V] - view the commands");
        System.out.println("[X] - exit the programm");
    }
}
