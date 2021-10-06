package com.exist.altheo.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;
import com.exist.altheo.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class PersonDao {
    private SessionFactory sessionFactory;

    public PersonDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    public void addPerson(
        String name, String address, double gwa, String zipCode,
        Date dateHired, boolean isCurrentlyEmployed, 
        Set<ContactInformation> contactInformations, List<Role> roles
    ) {
        Person person = 
        // new Person(gwa, zipCode, name, address, dateHired, 
        // isCurrentlyEmployed, contactInformations, roles);
        new Person(gwa, zipCode, name, address, dateHired, 
        isCurrentlyEmployed);

        person.setRoles(roles);

        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(person);

        session.getTransaction().commit();
        session.close();
    }


    //Helper method to select a specific contact
    @SuppressWarnings("unchecked")
    public List<Person> selectPerson(int selectedPersonId) {
        Session session = sessionFactory.openSession();
        String hsql_get_person = "FROM Person P WHERE P.personId = "+selectedPersonId;

        List<Person> results = session.createQuery(hsql_get_person).list();

        session.close();
        return results;
    }

    
    @SuppressWarnings("unchecked")
    public boolean updatePerson(
        double inputGwa, String inputZipCode, String inputName,
        String inputAddress, Date inputDateHired , boolean inputIsCurrentlyEmployed,
        int selectedPersonId
    ){
        Session session = sessionFactory.openSession();

        //Setting the update statement
        String hsql = "UPDATE Person"+ 
        " set gwa= : gwa ," + 
        "zipCode= : zipCode ," + 
        "name= : name ," + 
        "address= : address ," + 
        "dateHired= : dateHired ," + 
        "isCurrentlyEmployed= : isCurrentlyEmployed " + 
        "where personId= :personId";

        Query<Role> query = session.createQuery(hsql);
        query.setParameter("gwa", inputGwa);
        query.setParameter("zipCode", inputZipCode);
        query.setParameter("name", inputName);
        query.setParameter("address", inputAddress);
        query.setParameter("dateHired", inputDateHired);
        query.setParameter("isCurrentlyEmployed", inputIsCurrentlyEmployed);
        query.setParameter("personId", selectedPersonId);

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

    //TODO
    public void deletePerson(){

    }

    //TODO
    public void listPerson(){

    }
}
