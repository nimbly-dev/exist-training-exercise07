package com.exist.altheo.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PersonDao {
    private SessionFactory sessionFactory;

    public PersonDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    public void addPerson(
        String address, double gwa, String zipCode,
        LocalDate dateHired,LocalDate birthday,boolean isCurrentlyEmployed, String firstName,
        String middleName, String lastName, String suffix, String title
    ) {
        Person person =new Person(gwa, zipCode, firstName, middleName, lastName, suffix, title, 
        address, dateHired,birthday ,isCurrentlyEmployed);

        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(person);

        session.getTransaction().commit();
        session.close();
    }

    //Helper method to select a specific contact
    public Person selectPerson(int selectedPersonId) throws NoResultException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person person = session.get(Person.class, selectedPersonId);
    	// String hsql_eager_select_query = "SELECT p FROM Person p inner join p.roles where p.personId=1";
        // Person person = (Person) session.createQuery(hsql_eager_select_query).getSingleResult();

        session.getTransaction().commit();
        session.close();

        if(person == null){
            throw new NoResultException("Person id " + selectedPersonId + " does not exist");
        }else{
            return person;
        }
    }

    public void updatePerson(
        String address, double gwa, String zipCode,
        LocalDate dateHired, LocalDate inputBirthdate, boolean isCurrentlyEmployed, String firstName,
        String middleName, String lastName, String suffix, String title,
        int selectedPersonId
    ){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person person = session.get(Person.class, selectedPersonId);
        
        if(person == null)
            throw new NoResultException("Person id " + selectedPersonId + " does not exist");

        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setSuffix(suffix);
        person.setTitle(title);
        person.setAddress(address);
        person.setIsCurrentlyEmployed(isCurrentlyEmployed);
        person.setZipCode(zipCode);
        person.setGwa(gwa);
        person.setDateHired(dateHired);

        session.persist(person);
        session.getTransaction().commit();
        session.close();
 
    }

    public void deletePerson(int selectedPersonId)throws NoResultException{
        Session session = sessionFactory.openSession();
        // String hsql = "DELETE from Person where personId=:id";
        session.beginTransaction();

        Person person = session.get(Person.class, selectedPersonId);

        if(person == null)
            throw new NoResultException("Person id " + selectedPersonId + " does not exist");

        session.delete(person);

        // Query<Role> query = session.createQuery(hsql);
        // query.setParameter("id", selectedPersonId);

        // int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

    }

    @SuppressWarnings("unchecked")
    public List<Person> getListPerson(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Person> persons = session.createQuery("From Person").list();

        session.getTransaction().commit();
        session.close();

        if(persons.size() == 0)
            throw new NoResultException("No Persons found on database");
        else
            return persons;
    }
}
