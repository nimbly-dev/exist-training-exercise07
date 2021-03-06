package com.exist.altheo.service;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.xml.bind.ValidationException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.dao.ContactInformationDao;
import com.exist.altheo.dao.PersonDao;
import com.exist.altheo.dao.RoleDao;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class PersonControllerTest extends TestCase {

    private ContactInformationDao contactInformationDao;
    private PersonDao personDao;
    private RoleDao roleDao;
    private PersonController personController;

    private SessionFactory sessionFactory;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.personDao = new PersonDao();
        this.roleDao = new RoleDao();
        this.personController = new PersonController();
        this.contactInformationDao = new ContactInformationDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
		DBConnection.flushDbTables(sessionFactory);
        DBConnection.executeStartingSQLScript(sessionFactory);
	}

    @Test
    public void test_print_person_list(){
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(),  LocalDate.now(),
        false, "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1 , "555", LocalDate.of(2020, 2, 12),  LocalDate.now(), 
        false,  "Simba", "", "Zimba", "", "The Lion King");

        contactInformationDao.addContactInformation("2222", "8-7000", "jolibee@delivery.ph", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 2);
        roleDao.addRoleAndAssignToPerson("Mamba", 2);

        contactInformationDao.addContactInformation("(02) 86236", "96", "mcdo@delivery.ph", 2);

        personController.printPerson(personDao.getListPerson());
    }
    
    @Test
    public void test_print_person_list_by_gwa(){
        
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(),  LocalDate.now(),
        false, "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1 , "555", LocalDate.of(2020, 2, 12), LocalDate.now(), 
        false, "Simba", "", "Zimba", "", "The Lion King");

        contactInformationDao.addContactInformation("2222", "8-7000", "jolibee@delivery.ph", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 2);
        roleDao.addRoleAndAssignToPerson("Mamba", 2);

        contactInformationDao.addContactInformation("(02) 86236", "96", "mcdo@delivery.ph", 2);

        personController.printPersonsByGwa();
    }

    @Test
    public void test_print_person_list_by_last_name(){
        personDao.addPerson("Winterfell, The North",1 , "555", LocalDate.now(), LocalDate.now(), false, 
        "Jon", "", "Snow", "", "The King In the North");

        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), 
        LocalDate.now(),false, "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1.5 , "555", LocalDate.now(), LocalDate.now(),
        false, "Simba", "", "Zimba", "", "The Lion King");

        personController.printPersonsByLastName();
    }

    @Test 
    public void test_print_person_list_by_date_hired(){
        
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(),
        LocalDate.now(), false, "John", "Doo", "Doe", "", "The Third");

        personDao.addPerson("Manila City",1 , "555", LocalDate.of(2020, 2, 12),LocalDate.now(),
        false, "Simba", "", "Zimba", "", "The Lion King");

        contactInformationDao.addContactInformation("2222", "8-7000", "jolibee@delivery.ph", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 1);
        roleDao.addRoleAndAssignToPerson("Hecker", 1);

        roleDao.addRoleAndAssignToPerson("Admin", 2);

        contactInformationDao.addContactInformation("(02) 86236", "96", "mcdo@delivery.ph", 2);

        personController.printPersonsByDateHired();
    }

    @Test 
    public void test_add_person_controller_success(){
        try {
            personController.addPersonController("Ad", "mortem,", " inim??cus!", 
            "", "", 1.25, "Ashfield", "55", LocalDate.now(), LocalDate.now(),
            true);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void test_add_person_controller_with_blank_input_fail(){
      
        ValidationException exception = assertThrows(ValidationException.class, 
        ()-> personController.addPersonController("", ",", "", 
        "", "", 1.25, "Ashfield", "55", LocalDate.now(), LocalDate.now(),
        true));

        assertEquals(exception.getMessage(), "Input is blank, please fill out the fields");
    }
    @Test 
    public void test_add_person_controller_with_input_non_ascii_char_fail(){
        ValidationException exception = assertThrows(ValidationException.class, 
        ()-> personController.addPersonController("a", "a,", "??????????????????????????????", 
        "", "", 1.25, "Ashfield", "55", LocalDate.now(), LocalDate.now(),
        true));

        assertEquals(exception.getMessage(), "Input characters are not supported ");
    }

    @Test 
    public void test_update_person_controller_success(){
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(),LocalDate.now() ,false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

        try {
            personController.updatePersonController("Lo", "Mi", "noodles", "", 
            "The Fourth", 5, "Makati", "555", LocalDate.of(2000, 1, 12),LocalDate.now() ,true, 
            1);
        } catch (NoResultException | ValidationException e) {
            e.printStackTrace();
        }
    }

    
    @Test 
    public void test_update_person_controller_with_blank_input_fail(){
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.updatePersonController("Lo", "", "", "", 
        "The Fourth", 5, "Makati", "555", LocalDate.of(2000, 1, 12),LocalDate.now() ,true, 
        1));
        
        assertEquals(exception.getMessage(), "Input is blank, please fill out the fields");
    }

    @Test 
    public void test_update_person_controller_with_input_non_ascii_char_fail(){
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.updatePersonController("Lo", "Mi", "??????????????????????????????", "", 
        "The Fourth", 5, "Makati", "555", LocalDate.of(2000, 1, 12),LocalDate.now() ,true, 
        1));
        
        assertEquals(exception.getMessage(), "Input characters are not supported ");
    }

    @Test
    public void test_update_person_controller_with_nonexistent_person_id_fail(){
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personController.updatePersonController("Lo", "Mi", "noodles", "", 
        "The Fourth", 5, "Makati", "555", LocalDate.of(2000, 1, 12),LocalDate.now() ,true, 
        5));
        
        assertEquals(exception.getMessage(), "Person id " + 5 + " does not exist");
    }

    @Test 
    public void test_delete_person_controller_success(){
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), LocalDate.now(),false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

        personController.deletePersonController(1);
    }

    @Test 
    public void test_delete_person_controller_with_nonexistent_person_id_fail(){
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personController.deletePersonController(1));

        assertEquals(exception.getMessage(), "Person id " + 1 + " does not exist");
    }

    @Test
    public void test_assign_role_to_person_controller_success() {
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), LocalDate.now() ,false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

        try {
            personController.assignRoleToPersonController("Admin", 1);
        } catch (NoResultException | ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test_assign_role_to_person_controller_blank_input_fail() {
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.assignRoleToPersonController("", 1));

        assertEquals(exception.getMessage(), "Input is blank, please fill out the fields");
    }

    @Test
    public void test_assign_role_to_person_controller_non_ascii_input_fail() {
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.assignRoleToPersonController("??????????????????????????????", 1));

        assertEquals(exception.getMessage(), "Input characters are not supported ");
    }

    @Test
    public void test_assign_role_to_person_controller_non_existent_id_fail() {
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), LocalDate.now() ,false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

        PersistenceException exception = assertThrows(PersistenceException.class, 
        ()->personController.assignRoleToPersonController("Admin", 5));

        assertTrue(exception != null);
    }

    @Test
    public void test_assign_contact_to_person_controller_success() {
        personDao.addPerson("Manila City", 5, "555", LocalDate.now(), LocalDate.now() ,false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

        try {
            personController.assignContactInfoToPersonController("555", "222", 
            "gmail@gmail.com", 1);
        } catch (NoResultException | ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_assign_contact_to_person_controller_blank_input_fail() {
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.assignContactInfoToPersonController("", "", 
        "", 1));

        assertEquals(exception.getMessage(), "Input is blank, please fill out the fields");
    }

    @Test
    public void test_assign_contact_to_person_controller_non_ascii_input_fail() {
        ValidationException exception = assertThrows(ValidationException.class, 
        ()->personController.assignContactInfoToPersonController("1", "1", 
        "??????????????????????????????", 1));

        assertEquals(exception.getMessage(), "Input characters are not supported ");
    }

    @Test
    public void test_assign_contact_to_person_controller_non_existent_id_fail() {
        NoResultException exception = assertThrows(NoResultException.class, 
        ()->personController.assignContactInfoToPersonController("1", "1", 
        "2@gmail.com", 1));

        assertEquals(exception.getMessage(), "person id " + 1 + " does not exist");
    }

}
