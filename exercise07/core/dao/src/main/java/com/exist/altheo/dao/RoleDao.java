package com.exist.altheo.dao;

import java.util.List;

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

    public void addRole(String input){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(new Role(input));

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
    public boolean deleteRole(int selectedId){
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

    @SuppressWarnings("unchecked")
    public boolean setPersonToRole(int selectedRoleId,int personId ,String input) {
        Session session = sessionFactory.openSession();

        //Setting the update statement
        String hsql = "UPDATE Role set "
        +"roleName= :roleName ,"
        +"personId= :personId"
        +"where roleId= :roleId";

        Query<Role> query = session.createQuery(hsql);
        query.setParameter("roleId", selectedRoleId);
        query.setParameter("roleName", input);
        query.setParameter("personId", personId);

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
    

}
