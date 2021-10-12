package com.exist.altheo.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

	@Test
	public void test_run_starting_sql_script(){
		DBConnection.executeStartingSQLScript(sessionFactory);
	}

	@Test
	public void test_delete_db_tables(){
		DBConnection.flushDbTables(sessionFactory);
	}

}
