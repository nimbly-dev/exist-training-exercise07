package com.exist.altheo.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;
import javax.xml.catalog.Catalog;

import com.exist.altheo.dao.RoleDao;
import com.exist.altheo.model.Role;
import com.exist.altheo.utility.Reader;
import com.exist.altheo.view.Display;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

public class RoleController {
    private RoleDao roleDao;

    public RoleController(){
        roleDao = new RoleDao();
    }

    public void addNewRole(String input,int assignedPersonId)
        throws ValidationException
    {
        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            throw new ValidationException("Input must not be blank");
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            throw new ValidationException("Input must be a ascii char");
        }else{
            roleDao.addRoleAndAssignToPerson(input,assignedPersonId);
        }
    }

    public void deleteRole(int selectedRoleIdToDelete) throws NoResultException{
        roleDao.deleteRole(selectedRoleIdToDelete);
    }

    public void updateRole(String input, int selectedRoleIdToUpdate) throws ValidationException{ 

        //Returns true if valid otherwise returns false
        if(StringUtils.isEmpty(input)){
            throw new ValidationException("Input must not be blank");
        }
        else if(!StringUtils.isAsciiPrintable(input)){
            throw new ValidationException("Input must be a ascii char");
        }else{
            roleDao.updateRole(selectedRoleIdToUpdate, input);
        }
    }

    public void listAllRoles(){
        List<Role> rolesList= roleDao.getListsOfRoles();
        
        rolesList.stream().forEach((role)->{
            System.out.println("===================");
            System.out.println("Role Id: "+ role.getRoleId());
            System.out.println("Role Name: "+ role.getRoleName());
        });
         
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
                    try {
                        addNewRole(inputAdd,assignedPersonId);
                    } catch (ValidationException e) {
                       System.out.println(e.getMessage());
                    } catch(ConstraintViolationException e){ 
                        System.out.println(e.getMessage());
                    }
                    break;
                case "D":
                    int selectedRoleIdToDelete = Reader.readInt("Enter roleId you wish to delete ");
                    try {
                        deleteRole(selectedRoleIdToDelete);
                    } catch (NoResultException nre) {
                        System.out.println(nre.getMessage());
                    }
                    break;
                case "U":
                    String inputUpdate = Reader.readString("Enter rolename ");
                    int selectedRoleIdToUpdate = Reader.readInt("Enter roleId you wish to update");
                    try {
                        updateRole(inputUpdate,selectedRoleIdToUpdate);
                    } catch (NoResultException nre) {
                        System.out.println(nre.getMessage());
                    } catch (ValidationException ve) {
                        System.out.println(ve.getMessage());
                    }
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
