package com.exist.altheo;

import com.exist.altheo.model.ContactInformationTest;
import com.exist.altheo.model.PersonTest;
import com.exist.altheo.model.RoleTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ModelTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ModelTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(RoleTest.class);
        suite.addTestSuite(ContactInformationTest.class);
        suite.addTestSuite(PersonTest.class);
        return suite;
    }

}
