package com.exist.altheo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class RoleDaoTest extends TestCase {
	private PersonDao personDao;
    private RoleDao roleDao;
    private SessionFactory sessionFactory;

	//Data testfields for person
	private String testName;
	private double testGwa;
	private String testZipcode;
	private String testAddress;
	private Date testDate;
	private boolean testIsCurrentlyEmployed;
	private Set<ContactInformation> testContactInformations;
	private List<Role> testRoles;
    
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
		this.personDao = new PersonDao();

		//Sets test values for test person obj
		this.testName= "John Doe Doo Jr.";
		this.testGwa = 1.25;
		this.testZipcode = "Doo1";
		this.testAddress = "Winterfell, The North, Westeros";
		this.testDate = new Date();
		this.testIsCurrentlyEmployed = true;

		this.testContactInformations = new HashSet<ContactInformation>(0);
		this.testRoles = new ArrayList<Role>();
		
    }


    @Test
    public void test_add_role_success() {
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);
        String testInput = "Hecker";

		//Add the new role to db, id of 1
        roleDao.addRole(testInput,persons);

		//call the newly created role with id of 1
		List<Role> results = roleDao.selectRole(1);

		assertEquals(results.get(0).getRoleName(), testInput);
		assertTrue(results.get(0).getPersons() != null);
    }

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role(){
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);


		//Firstly, create a role named Admin with id of 1
		roleDao.addRole("Admin", persons);

		int selectedRoleId = 1;
		String input= "Hecker";

		boolean isUpdated = roleDao.updateRole(selectedRoleId, input);

		//Check if there is a role name hecker
		String hsql = "FROM Role R WHERE R.roleName = 'Hecker'";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query<Role> query = session.createQuery(hsql);

		List<Role> results = query.list();

		assertTrue(isUpdated == true);
		assertEquals(results.get(0).getRoleName(), input);

		session.close();
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void test_set_person_to_role(){
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);

		//Firstly, create a role named Admin with id of 1
		roleDao.addRole("Admin", persons);

		int selectedRoleId = 1;
		int selectedPersonId = 1;
		String input= "Hecker";


		boolean isUpdated = roleDao.setPersonToRole(selectedRoleId, selectedPersonId, input);

		//Check if there is a role name hecker
		String hsql = "FROM Role R WHERE R.roleName = 'Hecker'";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query<Role> query = session.createQuery(hsql);

		List<Role> results = query.list();

		assertTrue(isUpdated == true);
		assertEquals(results.get(0).getRoleName(), input);

		session.close();
	}

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role_fail(){
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);

		//Firstly, create a role named Admin with id of 1
		roleDao.addRole("Admin", persons);

		int selectedRoleId = 5;
		String input= "NotValid";

		boolean isUpdated = roleDao.updateRole(selectedRoleId, input);

		assertTrue(isUpdated == false);
		
	}

    @Test
	public void test_select_role(){
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);

		//Firstly, create a the roles
		roleDao.addRole("Admin", persons);
		roleDao.addRole("Hecker", persons);
		
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		
		assertTrue(listOfRoles.size() == 2);
		assertEquals(listOfRoles.get(0).getRoleName(), "Admin");
		assertEquals(listOfRoles.get(1).getRoleName(), "Hecker");
	}

	@Test
	public void test_select_role_empty(){	
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		
		assertTrue(listOfRoles.size() == 0);
	}

	@Test
	public void test_delete_role_success(){
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);

		//Firstly, create a the roles
		roleDao.addRole("Admin", persons);
		roleDao.addRole("Hecker", persons);

		int selectedId = 1; //Delete Admin

		boolean isDeleted = roleDao.deleteRole(selectedId);

		assertTrue(isDeleted == true);
		//Check if list length is equal to 1
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		assertTrue(listOfRoles.size() == 1);
	}

	@Test
	public void test_delete_role_with_invalid_input_fail() {
		//Add Person Obj first
		personDao.addPerson(testName, testAddress, testGwa, testZipcode, testDate, 
		testIsCurrentlyEmployed, testContactInformations, testRoles);

		//Place created person obj to a lit
		List<Person> persons = personDao.selectPerson(1);

		//Firstly, create a the roles
		roleDao.addRole("Admin", persons);
		roleDao.addRole("Hecker", persons);
		int selectedId = 5; //Delete Admin

		boolean isDeleted = roleDao.deleteRole(selectedId);

		assertTrue(isDeleted == false);
	}

}
