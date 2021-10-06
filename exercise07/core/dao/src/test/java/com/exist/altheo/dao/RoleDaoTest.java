package com.exist.altheo.dao;

import static org.junit.Assert.assertThrows;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

public class RoleDaoTest extends TestCase {
    private RoleDao roleDao;
    private SessionFactory sessionFactory;

	//Data testfields for person
	private String testName;
	private double testGwa;
	private String testZipcode;
	private String testAddress;
	private Date testDate;
	private boolean testIsCurrentlyEmployed;
    
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);

		//Sets test values for test person obj
		this.testName= "John Doe Doo Jr.";
		this.testGwa = 1.25;
		this.testZipcode = "Doo1";
		this.testAddress = "Winterfell, The North, Westeros";
		this.testDate = new Date();
		this.testIsCurrentlyEmployed = true;

    }

    @Test
    public void test_add_role_success() {
		Session session = sessionFactory.openSession();

		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		roleDao.addRole("Mamba", savedPersonId1);		
    }

	@Test
    public void test_add_role_invalid_assigned_person_id_fail() {
		
		PersistenceException exception = assertThrows(PersistenceException.class, 
			()->roleDao.addRole("Hotdog", 2));

		assertTrue(exception.getMessage() != null);//Check if there is an exception mssg
	
    }

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role_success(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Secondly, create a role named Admin with id of 1
		roleDao.addRole("Admin",savedPersonId1);
		int selectedRoleId = 1;
		String input= "Hecker";

		//Call the method
		boolean isUpdated = roleDao.updateRole(selectedRoleId, input);

		//Check if there is a role name hecker
		String hsql = "FROM Role R WHERE R.roleName = 'Hecker'";

		Session session_2 = sessionFactory.openSession();
		session_2.beginTransaction();

		Query<Role> query = session_2.createQuery(hsql);

		List<Role> results = query.list();

		assertTrue(isUpdated == true);
		assertEquals(results.get(0).getRoleName(), input);

		session_2.close();
	}

	
	@Test
	public void test_update_role_fail(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Secondly, create a role named Admin with id of 1
		roleDao.addRole("Admin",savedPersonId1);
		int selectedRoleId = 5;
		String input= "Hecker";

		//Call the method
		boolean isUpdated = roleDao.updateRole(selectedRoleId, input);

		assertTrue(isUpdated == false);
	}

    @Test
	public void test_select_role(){
		//Add Person Obj first
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles and assign them to person
		roleDao.addRole("Admin", savedPersonId1);
		roleDao.addRole("Hecker", savedPersonId1);
		
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
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles
		roleDao.addRole("Admin", savedPersonId1);
		roleDao.addRole("Hecker", savedPersonId1);

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
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int savedPersonId1 = (Integer) session.save(
			new Person( testGwa, testZipcode, testName, 
						testAddress, testDate, 
						testIsCurrentlyEmployed));

		session.getTransaction().commit();
		session.close();

		//Firstly, create a the roles
		roleDao.addRole("Admin", savedPersonId1);
		roleDao.addRole("Hecker", savedPersonId1);
		int selectedId = 5; //Delete Admin

		boolean isDeleted = roleDao.deleteRole(selectedId);

		assertTrue(isDeleted == false);
	}

}
