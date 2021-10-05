package com.exist.altheo;

import com.exist.altheo.dao.ContactInformationDaoTest;
import com.exist.altheo.dao.RoleDao;
import com.exist.altheo.dao.RoleDaoTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DaoTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DaoTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(RoleDaoTest.class);
        suite.addTestSuite(ContactInformationDaoTest.class);
        return suite;
    }

 
}
