package com.exist.altheo.dao;

import java.util.List;

import javax.persistence.NoResultException;

import com.exist.altheo.connection.DBConnection;
import com.exist.altheo.model.ContactInformation;
import com.exist.altheo.model.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ContactInformationDao {
    private SessionFactory sessionFactory;

    public ContactInformationDao(){
        this.sessionFactory = DBConnection.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public void addContactInformation(
        String landline, String mobileNumber, String email, int personSelectId
    ) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        int getContactId = (Integer) session.save(new ContactInformation(landline, mobileNumber, email));

        ContactInformation contactInformation = session.get(ContactInformation.class, getContactId);
        contactInformation.setPerson(session.get(Person.class, personSelectId));

        session.update(contactInformation);

        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public void updateContactInformation(
        int selectedContactId, String inputlandline, String inputMobileNumber,
        String inputEmail
    ) {
        Session session = sessionFactory.openSession();

        //Setting the update statement
        String hsql = "UPDATE ContactInformation set "
        +"landline= :landline ,"
        +"mobileNumber= :mobileNumber,"
        +"email= :email "
        +"where contactId= :contactId";

        Query<ContactInformation> query = session.createQuery(hsql);
        query.setParameter("landline", inputlandline);
        query.setParameter("mobileNumber", inputMobileNumber);
        query.setParameter("email", inputEmail);
        query.setParameter("contactId", selectedContactId);

        //Begin updating
        session.beginTransaction();
        int result = query.executeUpdate();

        session.getTransaction().commit();

        session.close();

        if(result <= 0){
            throw new NoResultException("Role id " + selectedContactId + " does not exist");
        }
    }

    @SuppressWarnings("unchecked")
    public void deleteContact(
        int selectedContactId
    )throws NoResultException{
        Session session = sessionFactory.openSession();

        String hsql = "DELETE from ContactInformation where contactId=:id";

        Query<ContactInformation> query = session.createQuery(hsql);
        query.setParameter("id", selectedContactId);

        session.beginTransaction();
        int result = query.executeUpdate();

        session.getTransaction().commit();
        session.close();

        if(result <= 0){
            throw new NoResultException("Role id " + selectedContactId + " does not exist");
        }
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
