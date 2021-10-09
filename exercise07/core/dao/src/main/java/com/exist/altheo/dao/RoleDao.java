package com.exist.altheo.dao;

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

public class RoleDao {
    private SessionFactory sessionFactory;

    public RoleDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    public void addNewRole(String input) throws ConstraintViolationException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(new Role(input));
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked") //TODO - BUG, OVERRIDES person_id if it currently points to another column
    public void assignRoleToPerson(int selectedPersonId, String roleName){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        // int personId = session.save(new Person());

        String nativeQueryAssignPerson = "UPDATE Role SET person_id =:personId where role_name =:roleName";
        Query<Role> query = session.createSQLQuery(nativeQueryAssignPerson);
        query.setParameter("personId", selectedPersonId);
        query.setParameter("roleName", roleName);

        query.executeUpdate();
        
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked") //TODO - CONVERT THIS TO USE session instead of native sql
    public void addRoleAndAssignToPerson(String input, int selectedPersonId) 
        throws PersistenceException, ConstraintViolationException{
        Session session = sessionFactory.openSession();

		session.beginTransaction();

		int savedRoleId = (Integer) session.save(new Role(input));

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

    public void updateRole(int selectedRoleId ,String input)throws NoResultException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role = session.get(Role.class, selectedRoleId);

        if(role == null)
            throw new NoResultException("Role id " + selectedRoleId + " does not exist");
        
        role.setRoleName(input);
        session.update(role);

        session.getTransaction().commit();
        session.close();
 
    }

    @SuppressWarnings("unchecked")
    public List<Role> getListsOfRoles()throws NoResultException{
        Session session = sessionFactory.openSession();

        session.beginTransaction();
		List<Role> result = session.createQuery( "from Role" ).list();
        session.getTransaction().commit();
        session.close();

        if(result.size() == 0){
            throw new NoResultException("No roles found on database");
        }else{
            return result;
        }
    }

    @SuppressWarnings("unchecked") //TODO - Bug, person obj reference on a specified role must be deleted also
    public void deleteRole(int selectedId) throws NoResultException
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Role role = session.get(Role.class, selectedId);

        if(role == null)
            throw new NoResultException("Role id " + selectedId + " does not exist");

        //Unreferenced or delete the role on the person obj
        //First get the foreign key personId from the specified role
        String native_query_find_fk = "SELECT person_id FROM Role WHERE role_id =:roleId";
        Query<Integer> query_role = session.createSQLQuery(native_query_find_fk);
        query_role.setParameter("roleId", selectedId);
        //Place the personId on a variable
        List<Integer> personId = query_role.list();

        //Now use the personId to Unreferenced or delete the role on the person obj
        Person person = session.get(Person.class, personId.get(0));
        //Delete the role on the person obj roles list
        // person.getRoles().removeIf(r-> r.getRoleId() == selectedId);
        person.getRoles().remove(0);

        session.update(person);

        session.delete(session.get(Role.class, selectedId));

        session.getTransaction().commit();
        session.close();
    }

    //Helper method to select a specific contact
    public Role selectRole(int selectedRoleId) throws NoResultException {
        Session session = sessionFactory.openSession();
        
        Role role = session.get(Role.class, selectedRoleId);
    
        session.close();

        if(role == null){
            throw new NoResultException("Role id " + selectedRoleId + " does not exist");
        }else{
            return role;
        }
    }
    

}
