package com.exist.altheo.dao;


import javax.persistence.NoResultException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ContactInformationDao {
    private SessionFactory sessionFactory;

    public ContactInformationDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

   
    public void addContactInformation(
        String landline, String mobileNumber, String email, int personSelectId
    ) throws NoResultException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        int getContactId = (Integer) session.save(new ContactInformation(landline, mobileNumber, email));
      
        ContactInformation contactInformation = session.get(ContactInformation.class, getContactId);

        if(session.get(Person.class, personSelectId) == null)
            throw new NoResultException("person id " + personSelectId + " does not exist");
        

        contactInformation.setPerson(session.get(Person.class, personSelectId));

        session.update(contactInformation);

        session.getTransaction().commit();
        session.close();
    }

    public void updateContactInformation(
        int selectedContactId, String inputlandline, String inputMobileNumber,
        String inputEmail
    ) throws NoResultException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Setting the update statement

        ContactInformation contactInformation = session.get(ContactInformation.class, selectedContactId);

        if(contactInformation == null)
            throw new NoResultException("Person id " + selectedContactId + " does not exist");

        contactInformation.setLandline(inputlandline);
        contactInformation.setMobileNumber(inputMobileNumber);
        contactInformation.setEmail(inputEmail);


        session.getTransaction().commit();

        session.close();

    }

    public void deleteContact(
        int selectedContactId
    )throws NoResultException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();


        ContactInformation contactInformation = session.get(ContactInformation.class, selectedContactId);

        if(contactInformation == null)
            throw new NoResultException("person id " + selectedContactId + " does not exist");

        session.delete(contactInformation);
        session.getTransaction().commit();
        session.close();

    }

    //Helper method to select a specific contact
    public ContactInformation selectContact(int selectedContactId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ContactInformation contactInformation = session.get(ContactInformation.class, selectedContactId);
    
        session.close();

        if(contactInformation == null){
            throw new NoResultException("Role id " + selectedContactId + " does not exist");
        }else{
            return contactInformation;
        }
    }
}
