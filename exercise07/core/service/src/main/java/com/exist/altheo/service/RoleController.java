package com.exist.altheo.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.exist.altheo.dao.RoleDao;
import com.exist.altheo.model.Role;
import com.exist.altheo.utility.Reader;
import com.exist.altheo.view.Display;

import org.apache.commons.lang3.StringUtils;

public class RoleController {
    private RoleDao roleDao;

    public RoleController(){
        roleDao = new RoleDao();
    }

    public boolean addNewRole(String input,int assignedPersonId) {
        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            System.out.println("Input is blank, please fill out the fields");
            return false;
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            System.out.println("Input is invalid");
            return false;
        }else{
            roleDao.addRoleAndAssignToPerson(input,assignedPersonId);
            return true;
        }
    }

    public void deleteRole() {
        int selectedIndex = Reader.readInt("Enter roleId you wish to delete ");
        try{
            roleDao.deleteRole(selectedIndex);
        }catch(NoResultException nre){
            System.out.println(nre.getMessage());
        }
    }

    public void updateRole(String input) {
        int selectedIndex = Reader.readInt("Enter roleId you wish to update");

        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            System.out.println("Input is blank, please try again");
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            System.out.println("Input is invalid");
        }else{
            try{
                roleDao.updateRole(selectedIndex, input);
            }catch(NoResultException nre){
                System.out.println(nre.getMessage());
            }
        }
    }

    public void listAllRoles(){
        List<Role> rolesList= roleDao.getListsOfRoles();
        
        rolesList.stream().forEach((role)->
            System.out.println("Role Name: "+role.getRoleName())
        );
    }

    //The Role Function UI
    public void roleUserInterface() {
        System.out.println("You are now in the Role Functionality Interface");
        boolean isEndRoleUserInterface = false;
        

        do {
            String command = Reader.readString("Enter command: ");
            switch (command.toUpperCase()) {
                case "A":
                    String inputAdd = Reader.readString("Enter rolename ");
                    int assignedPersonId = Reader.readInt("Enter assigned person id ");
                    addNewRole(inputAdd,assignedPersonId);
                    break;
                case "D":
                    deleteRole();
                    break;
                case "U":
                    String inputUpdate = Reader.readString("Enter rolename ");
                    updateRole(inputUpdate);
                    break;
                case "L":
                    listAllRoles();
                    break;
                case "V":
                    Display.displayRoleInterfaceCommands();
                    break;
                default:
                    System.out.println("Wrong input, please put a valid input");
                    break;
            }
        } while (isEndRoleUserInterface);
    }
}
