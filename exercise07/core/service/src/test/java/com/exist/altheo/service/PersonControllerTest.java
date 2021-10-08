package com.exist.altheo.service;

import java.time.LocalDate;

import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.dao.RoleDao;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class PersonControllerTest extends TestCase {

    private PersonDao personDao;
    private RoleDao roleDao;
    private PersonController personController;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.personDao = new PersonDao();
        this.roleDao = new RoleDao();
        this.personController = new PersonController();
	}
    
    @Test
    public void test_print_person_list_by_gwa(){
        

        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), false, 
        "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1 , "555", LocalDate.of(2020, 2, 12), false, 
        "Simba", "", "Zimba", "", "The Lion King");

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 2);

        personController.printPersonsByGwa();
    }

    @Test
    public void test_print_person_list_by_last_name(){
        personDao.addPerson("Winterfell, The North",1 , "555", LocalDate.now(), false, 
        "Jon", "", "Snow", "", "The King In the North");

        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), false, 
        "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1.5 , "555", LocalDate.now(), false, 
        "Simba", "", "Zimba", "", "The Lion King");

        personController.printPersonsByLastName();
    }
}
