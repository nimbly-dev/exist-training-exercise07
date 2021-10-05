package com.exist.altheo.dao;

import java.util.List;

import com.exist.altheo.connection.DBConnection;
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
    
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        this.roleDao = new RoleDao();
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_add_role_success() {
        String testInput = "Hecker";

        roleDao.addRole(testInput);

        String hsql = "FROM Role R WHERE R.roleName = '"+testInput+"'";

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query<Role> query = session.createQuery(hsql);

		List<Role> results = query.list();

		assertEquals(results.get(0).getRoleName(), testInput);
		
		session.close();
        
    }

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role(){
		RoleDao roleDao = new RoleDao();

		//Firstly, create a role named Admin with id of 1
		roleDao.addRole("Admin");

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

	
	// @SuppressWarnings("unchecked")
	// @Test
	// public void test_set_person_to_role(){
	// 	RoleDao roleDao = new RoleDao();

	// 	//Firstly, create a role named Admin with id of 1
	// 	roleDao.addRole("Admin");

	// 	int selectedRoleId = 1;
	// 	int selectedPersonId = 1;
	// 	String input= "Hecker";


	// 	boolean isUpdated = roleDao.setPersonToRole(selectedRoleId, selectedPersonId, input);

	// 	//Check if there is a role name hecker
	// 	String hsql = "FROM Role R WHERE R.roleName = 'Hecker'";

	// 	Session session = sessionFactory.openSession();
	// 	session.beginTransaction();

	// 	Query<Role> query = session.createQuery(hsql);

	// 	List<Role> results = query.list();

	// 	assertTrue(isUpdated == true);
	// 	assertEquals(results.get(0).getRoleName(), input);

	// 	session.close();
	// }

    @SuppressWarnings("unchecked")
	@Test
	public void test_update_role_fail(){
		RoleDao roleDao = new RoleDao();

		//Firstly, create a role named Admin with id of 1
		roleDao.addRole("Admin");

		int selectedRoleId = 5;
		String input= "NotValid";

		boolean isUpdated = roleDao.updateRole(selectedRoleId, input);

		assertTrue(isUpdated == false);
		
	}

    @Test
	public void test_select_role(){
		RoleDao roleDao = new RoleDao();

		//Firstly, create a the roles
		roleDao.addRole("Admin");
		roleDao.addRole("Hecker");
		
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		
		assertTrue(listOfRoles.size() == 2);
		assertEquals(listOfRoles.get(0).getRoleName(), "Admin");
		assertEquals(listOfRoles.get(1).getRoleName(), "Hecker");
	}

	@Test
	public void test_select_role_empty(){
		RoleDao roleDao = new RoleDao();

		
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		
		assertTrue(listOfRoles.size() == 0);
	}

	@Test
	public void test_delete_role_success(){
		RoleDao roleDao = new RoleDao();

		//Firstly, create a the roles
		roleDao.addRole("Admin");
		roleDao.addRole("Hecker");

		int selectedId = 1; //Delete Admin

		boolean isDeleted = roleDao.deleteRole(selectedId);

		assertTrue(isDeleted == true);
		//Check if list length is equal to 1
		List<Role> listOfRoles = roleDao.getListsOfRoles();
		assertTrue(listOfRoles.size() == 1);
	}

	@Test
	public void test_delete_role_with_invalid_input_fail() {
		RoleDao roleDao = new RoleDao();

		//Firstly, create a the roles
		roleDao.addRole("Admin");
		roleDao.addRole("Hecker");

		int selectedId = 5; //Delete Admin

		boolean isDeleted = roleDao.deleteRole(selectedId);

		assertTrue(isDeleted == false);
	}

}
