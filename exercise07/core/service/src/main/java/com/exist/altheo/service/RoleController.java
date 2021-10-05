package com.exist.altheo.service;

import java.util.List;

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

    public boolean addNewRole(String input) {
        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            System.out.println("Input is blank, please try again");
            return false;
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            System.out.println("Input is invalid");
            return false;
        }else{
            // roleDao.addRole(input);
            return true;
        }
    }

    public void deleteRole() {
        int selectedIndex = Reader.readInt("Enter roleId you wish to delete ");
        roleDao.deleteRole(selectedIndex);
    }

    public boolean updateRole(String input) {
        int selectedIndex = Reader.readInt("Enter roleId you wish to update");

        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            System.out.println("Input is blank, please try again");
            return false;
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            System.out.println("Input is invalid");
            return false;
        }else{
            roleDao.updateRole(selectedIndex, input);
            return true;
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
        
        String command = Reader.readString("Enter command: ");

        do {
            switch (command.toUpperCase()) {
                case "A":
                    String inputAdd = Reader.readString("Enter rolename ");
                    addNewRole(inputAdd);
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
