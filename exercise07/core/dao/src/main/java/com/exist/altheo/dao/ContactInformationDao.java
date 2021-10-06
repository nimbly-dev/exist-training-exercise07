package com.exist.altheo.dao;

import java.util.List;

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

    public void addContactInformation(
        String landline, String mobileNumber, String email
    ) {
        Session session = sessionFactory.openSession();

        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setLandline(landline);
        contactInformation.setMobileNumber(mobileNumber);
        contactInformation.setEmail(email);

        session.beginTransaction();
        session.save(contactInformation);

        session.getTransaction().commit();
        session.close();
    }

    public void addContactInformation(
        String landline, String mobileNumber, String email, Person person
    ) {
        Session session = sessionFactory.openSession();

        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setLandline(landline);
        contactInformation.setMobileNumber(mobileNumber);
        contactInformation.setEmail(email);
        // contactInformation.setPerson(per);
        contactInformation.setPerson(person);

        session.beginTransaction();
        session.save(contactInformation);

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
        query.executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @SuppressWarnings("unchecked")
    public boolean deleteContact(
        int selectedContactId
    ){
        Session session = sessionFactory.openSession();

        String hsql = "DELETE from ContactInformation where contactId=:id";

        Query<ContactInformation> query = session.createQuery(hsql);
        query.setParameter("id", selectedContactId);

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

    //Helper method to select a specific contact
    @SuppressWarnings("unchecked")
    public List<ContactInformation> selectContact(int selectedContact) {
        Session session = sessionFactory.openSession();
        String hsql_get_person = "FROM ContactInformation C WHERE C.contactId = "+selectedContact;

        List<ContactInformation> results = session.createQuery(hsql_get_person).list();

        session.close();
        return results;
    }
}
