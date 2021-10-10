package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

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
    @BeforeAll
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

	
    @Override
    @AfterEach
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Test
	public void test_add_role_success(){
		String testInput = "Admin";

		//ID of 1
		roleDao.addNewRole(testInput);

		Role selectRole = roleDao.selectRole(1);

		assertEquals(selectRole.getRoleName(), testInput);
	}


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
		PersonDao personDao = new PersonDao();
		personDao.addPerson("Manila City", 5, "555", LocalDate.now(), false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

		roleDao.addRoleAndAssignToPerson("Mamba", 1); //ID OF 1

		List<Person> persons = personDao.getListPerson();

		assertEquals(persons.get(0).getRoles().get(0).getRoleName(), "Mamba");
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

    @Test //TODO - FIX BUG, STUCK ON EXECUTING TEST CASE UPON BULK TEST EXECUTION
	public void test_select_role(){
		//Add Person Obj first
		new PersonDao().addPerson(testAddress, testGwa, testZipcode, testDate, testIsCurrentlyEmployed, 
		testFirstName, testMiddleName, testLastName, testSuffix, testTitle); //ID OF 1

		// Firstly, create the role and assign them to person
		roleDao.addRoleAndAssignToPerson("Admin", 1);//ID OF 1
		Role role = roleDao.selectRole(1);
		assertEquals(role.getRoleName(), "Admin");
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

		List<Role> roles = roleDao.getListsOfRoles();

		assertEquals(roles.size(), 1);
		assertEquals(roles.get(0).getRoleName(), "Hecker");
	}

	@Test
	public void test_delete_role_with_nonexistent_id_input_fail() {
		int selectedId = 5; //Delete Admin

		NoResultException exception = assertThrows(NoResultException.class, 
        ()->roleDao.deleteRole(selectedId));

		assertEquals(exception.getMessage(), "Role id " + selectedId + " does not exist");
	}

	@Test
	public void test_list_all_roles() {
		new PersonDao().addPerson("Manila City", 5, "555", LocalDate.now(), false, 
        "John", "Doo", "Doe", "", "The Third"); //ID OF 1

		roleDao.addRoleAndAssignToPerson("Hotdog", 1);
		roleDao.addRoleAndAssignToPerson("Cheesedog", 1);
		roleDao.addRoleAndAssignToPerson("Corndog", 1);
		
		List<Role> roles = roleDao.getListsOfRoles();

		roles.stream().forEach((r)->{
			System.out.println(r.getRoleName());
		});
	}

}
