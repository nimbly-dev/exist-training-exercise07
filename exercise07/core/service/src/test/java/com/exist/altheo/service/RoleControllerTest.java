package com.exist.altheo.service;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;

import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.dao.RoleDao;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class RoleControllerTest extends TestCase {
    private RoleDao roleDao;
    private PersonDao personDao;
    private RoleController roleController;

    //TODO - CREATE A GLOBAL ROLE AND PERSON OBJ
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleController = new RoleController();
        this.roleDao = new RoleDao();
        this.personDao = new PersonDao();
	}

    @Test 
    public void test_list_all_roles(){
        personDao.addPerson("Makati City", 1.25, "77", LocalDate.now(), true, 
        "Altheo", "Colico", "Saquilayan", "", ""); //ID of 1

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("For Honor", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);

        roleController.listAllRolesController();
    }

    @Test 
    public void test_add_role_controller_success(){
        personDao.addPerson("Makati City", 1.25, "77", LocalDate.now(), true, 
        "Altheo", "Colico", "Saquilayan", "", ""); //ID of 1

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("For Honor", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);
    }

    @Test 
    public void test_add_role_controller_with_blank_input_fail(){
        personDao.addPerson("Makati City", 1.25, "77", LocalDate.now(), true, 
        "Altheo", "Colico", "Saquilayan", "", ""); //ID of 1

        ValidationException exception = assertThrows(ValidationException.class, 
        ()->roleController.addNewRoleController("", 1));

        assertEquals(exception.getMessage(), "Input must not be blank");
    }

    @Test 
    public void test_add_role_controller_with_nonascii_input_fail(){
        personDao.addPerson("Makati City", 1.25, "77", LocalDate.now(), true, 
        "Altheo", "Colico", "Saquilayan", "", ""); //ID of 1

        ValidationException exception = assertThrows(ValidationException.class, 
        ()->roleController.addNewRoleController("手田水口火竹口竹火竹", 1));

        assertEquals(exception.getMessage(), "Input must be a ascii char");
    }

    @Test 
    public void test_update_role_controller_success(){
        roleDao.addRoleAndAssignToPerson("Admin", 1); //ID OF 1

        try {
            roleController.updateRoleController("Hecker",1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void test_update_role_controller_with_blank_input_fail(){
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->roleController.updateRoleController("", 1));

        assertEquals(exception.getMessage(), "Input must not be blank");
    }

    @Test 
    public void test_update_role_controller_with_nonascii_input_fail(){
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->roleController.updateRoleController("手田水口火竹口竹火竹", 1));

        assertEquals(exception.getMessage(), "Input must be a ascii char");
    }

    @Test 
    public void test_update_role_controller_with_nonexistent_role_id_fail(){
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleController.updateRoleController("Hecker", 5));

        assertEquals(exception.getMessage(), "Role id " + 5 + " does not exist");
    }

    @Test //TODO - Bug, person obj reference on a specified role must be deleted also. FAIL TESTCASE
    public void test_delete_role_controller_success(){
        personDao.addPerson("Makati City", 1.25, "77", LocalDate.now(), true, 
        "Altheo", "Colico", "Saquilayan", "", ""); //ID of 1

        roleDao.addRoleAndAssignToPerson("Lobm", 1); //ROLE ID OF 1

        roleController.deleteRoleController(1);
    }

    @Test //TODO
    public void test_delete_role_controller_with_nonexistent_role_id_fail(){
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleController.deleteRoleController(5));

        assertEquals(exception.getMessage(), "Role id " + 5 + " does not exist");
    }
}
