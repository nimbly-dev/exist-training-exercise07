package com.exist.altheo.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class RoleDao {
    private SessionFactory sessionFactory;

    public RoleDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public void addRole(String input, int selectedPersonId) throws PersistenceException{
        Session session = sessionFactory.openSession();

		session.beginTransaction();

		int savedRoleId = (Integer) session.save(new Role(input));
	
		System.out.println("ROLEID : "+savedRoleId);

		//Update the newly created role to reference the person
		// String hsql_role = "UPDATE Role set person.persons.personId= :personId where roleId= :roleId";
		String native_query_role = "UPDATE Role SET person_id =:personId where role_id =:roleId";

		Query<Role> query_role = session.createSQLQuery(native_query_role);
        query_role.setParameter("roleId", savedRoleId);
        query_role.setParameter("personId", selectedPersonId);

		query_role.executeUpdate();

		//Update the newly created person to reference the role
		// String hsql_person = "UPDATE PERSON set roles.role.roleId= :roleId where personId= :personId";
		String native_query_person = "UPDATE Person SET role_id =:roleId where person_id =:personId";

		Query<Role> query_person = session.createSQLQuery(native_query_person);
        query_person.setParameter("roleId", savedRoleId);
        query_person.setParameter("personId", selectedPersonId);

		query_person.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public boolean updateRole(int selectedRoleId ,String input){
        Session session = sessionFactory.openSession();

        //Setting the update statement
        String hsql = "UPDATE Role set roleName= :roleName where roleId= :roleId";

        Query<Role> query = session.createQuery(hsql);
        query.setParameter("roleId", selectedRoleId);
        query.setParameter("roleName", input);

        //Begin updating
        session.beginTransaction();
        int result = query.executeUpdate();

        session.getTransaction().commit();

        session.close();
        if(result <= 0){
            return false;
        }else{
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Role> getListsOfRoles(){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
		List<Role> result = session.createQuery( "from Role" ).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @SuppressWarnings("unchecked")
    public boolean deleteRole(int selectedId)
    {
        Session session = sessionFactory.openSession();

        String hsql = "DELETE from Role where roleId=:id";

        Query<Role> query = session.createQuery(hsql);
        query.setParameter("id", selectedId);

        session.beginTransaction();
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        if(result <= 0){
            return false;
        }else{
            return true;
        }
    }

    // @SuppressWarnings("unchecked")
    // public boolean setPersonToRole(int selectedContactId,int personId ,String input) {
    //     Session session = sessionFactory.openSession();

    //     //Setting the update statement
    //     String hsql = "UPDATE Role set "
    //     +"roleName= :roleName ,"
    //     +"personId= :personId"
    //     +"where contactId= :contactId";

    //     Query<Role> query = session.createQuery(hsql);
    //     query.setParameter("contactId", selectedContactId);
    //     query.setParameter("roleName", input);
    //     query.setParameter("personId", personId);

    //     //Begin updating
    //     session.beginTransaction();
    //     int result = query.executeUpdate();

    //     session.getTransaction().commit();

    //     session.close();
    //     if(result <= 0){
    //         return false;
    //     }else{
    //         return true;
    //     }   
    // }


    //Helper method to select a specific contact
    @SuppressWarnings("unchecked")
    public List<Role> selectRole(int selectedRoleId) {
        Session session = sessionFactory.openSession();
        String hsql_get_person = "FROM Role R WHERE R.roleId = "+selectedRoleId;
        
        List<Role> results = session.createQuery(hsql_get_person).list();

        session.close();
        return results;
    }
    

}
