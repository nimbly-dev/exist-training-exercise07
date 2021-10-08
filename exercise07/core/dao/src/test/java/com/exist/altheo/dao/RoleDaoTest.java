package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class RoleDaoTest extends TestCase {
    private RoleDao roleDao;
    private SessionFactory sessionFactory;

	//Data testfields for person
	private String testFirstName;
	private String testLastName;
	private String testMiddleName;
	private String testTitle;
	private String testSuffix;
	private double testGwa;
	private String testZipcode;
	private String testAddress;
	private LocalDate testDate;
	private boolean testIsCurrentlyEmployed;
    
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);

		//Sets test values for test person obj
		this.testFirstName = "John";
		this.testMiddleName = "Doo";
		this.testLastName = "Doe";
		this.testSuffix = "Jr.";
		this.testTitle = "The Third";
		this.testGwa = 1.25;
		this.testZipcode = "Doo1";
		this.testAddress = "Winterfell, The North, Westeros";
		this.testDate = LocalDate.now();
		this.testIsCurrentlyEmployed = true;
    }

	@Test
	public void test_add_role_success(){
		String testInput = "Admin";

		//ID of 1
		roleDao.addNewRole(testInput);

		Role selectRole = roleDao.selectRole(1);

		assertEquals(selectRole.getRoleName(), testInput);
	}

	// @Test
	// public void test_add_role_duplicate_fail(){
	// 	roleDao.addNewRole("Admin");

	// 	ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, 
	// 	()->roleDao.addNewRole("Admin"));

	// 	assertTrue(exception.getMessage() != null);//Check if there is an exception mssg
	// }

	@Test
	public void test_assign_role_to_person_test(){
		roleDao.addNewRole("Admin");//ID OF 1

		//Create a person test obj
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));
			
		session.getTransaction().commit();
		session.close();

		//Create a new Role
		roleDao.assignRoleToPerson(savedPersonId1, "Admin");

		//Get the person
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		
		Person getPerson = session2.get(Person.class, savedPersonId1);
	
		assertEquals(getPerson.getRoles().get(0).getRoleName(), "Admin");

		session2.getTransaction().commit();
		session2.close();
	}

    @Test
    public void test_add_role_and_assign_to_person_success() {
		Session session = sessionFactory.openSession();

		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));
		

		session.getTransaction().commit();
		session.close();

		roleDao.addRoleAndAssignToPerson("Mamba", savedPersonId1);		
    }

	@Test
    public void test_add_role_and_assign_to_invalid_assigned_person_id_fail() {
		
		PersistenceException exception = assertThrows(PersistenceException.class, 
			()->roleDao.addRoleAndAssignToPerson("Hotdog", 2));

		assertTrue(exception.getMessage() != null);//Check if there is an exception mssg
	
    }

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role_success(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Secondly, create a role named Admin with id of 1
		roleDao.addRoleAndAssignToPerson("Admin",savedPersonId1);
		int selectedRoleId = 1;
		String input= "Hecker";

		//Call the method
		roleDao.updateRole(selectedRoleId, input);

		//Check if there is a role name hecker
		String hsql = "FROM Role R WHERE R.roleName = 'Hecker'";

		Session session_2 = sessionFactory.openSession();
		session_2.beginTransaction();

		Query<Role> query = session_2.createQuery(hsql);

		List<Role> results = query.list();

		assertEquals(results.get(0).getRoleName(), input);

		session_2.close();
	}

	
	@Test
	public void test_update_role_fail(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Secondly, create a role named Admin with id of 1
		roleDao.addRoleAndAssignToPerson("Admin",savedPersonId1);
		int selectedRoleId = 5;
		String input= "Hecker";


		NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleDao.updateRole(selectedRoleId, input));

		assertEquals(exception.getMessage(), "Role id " + selectedRoleId + " does not exist");
	}

    @Test
	public void test_select_role(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles and assign them to person
		roleDao.addRoleAndAssignToPerson("Admin", savedPersonId1);
		roleDao.addRoleAndAssignToPerson("Hecker", savedPersonId1);
		
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		
		assertTrue(listOfRoles.size() == 2);
		assertEquals(listOfRoles.get(0).getRoleName(), "Admin");
		assertEquals(listOfRoles.get(1).getRoleName(), "Hecker");
	}

	@Test
	public void test_select_role_empty(){	
		NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleDao.getListsOfRoles());

		assertEquals(exception.getMessage(), "No roles found on database");
	}

	@Test
	public void test_delete_role_success(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles
		roleDao.addRoleAndAssignToPerson("Admin", savedPersonId1);
		roleDao.addRoleAndAssignToPerson("Hecker", savedPersonId1);

		int selectedId = 1; //Delete Admin

		roleDao.deleteRole(selectedId);

		//Check if list length is equal to 1
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		assertTrue(listOfRoles.size() == 1);
	}

	@Test
	public void test_delete_role_with_nonexistent_id_input_fail() {
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person(testGwa, testZipcode, testFirstName, testMiddleName, testLastName, 
			testSuffix, testTitle, testAddress, testDate, testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles
		roleDao.addRoleAndAssignToPerson("Admin", savedPersonId1);
		roleDao.addRoleAndAssignToPerson("Hecker", savedPersonId1);
		int selectedId = 5; //Delete Admin

		NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleDao.deleteRole(selectedId));

		assertEquals(exception.getMessage(), "Role id " + selectedId + " does not exist");
		
	}

}
