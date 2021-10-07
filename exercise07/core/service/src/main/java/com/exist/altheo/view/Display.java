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
        System.out.println("[R] assign a role to specified person information  ");
        System.out.println("[C] assign a contact to specified person information  ");
        System.out.println("[X] exit the Contact Information Interface ");
    }
}
