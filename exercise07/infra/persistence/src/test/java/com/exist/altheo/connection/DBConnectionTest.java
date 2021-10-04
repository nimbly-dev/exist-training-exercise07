package com.exist.altheo.connection;

import junit.framework.TestCase;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;


public class DBConnectionTest extends TestCase {
    private SessionFactory sessionFactory;

	@Override
	@Before
	protected void setUp() throws Exception {
        // System.out.println("IRUN MO MUNA TOH!!!");
		sessionFactory = DBConnection.setSessionFactory(sessionFactory);
	}

	@Test
	public void test_connection(){
		Session session = sessionFactory.openSession();

		assertTrue(session != null);

		session.close();
	}


}
