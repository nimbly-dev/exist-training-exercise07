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

    //TODO
    public void updatePerson(){

    }

    //TODO
    public void deletePerson(){

    }

    //TODO
    public void listPerson(){

    }
}
