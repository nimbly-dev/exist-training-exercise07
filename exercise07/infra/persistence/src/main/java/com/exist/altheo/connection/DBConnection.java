package com.exist.altheo.connection;

import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DBConnection {
    
    public static SessionFactory setSessionFactory( SessionFactory sessionFactory) {
        // A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        try {
            System.out.println("ON TRY BLOCK");  
            sessionFactory =  new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            // sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        }
        catch (MappingException e){
           System.out.println("MAPPING EXCEPTION");
           System.out.println(e.getMessage());
           StandardServiceRegistryBuilder.destroy( registry );
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return sessionFactory;
    }
}
