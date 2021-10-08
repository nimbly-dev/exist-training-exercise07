package com.exist.altheo;


import java.util.Date;

import com.exist.altheo.utility.Reader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ReaderTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReaderTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ReaderTest.class );
    }

    @org.junit.jupiter.api.Test
    public void test_input_date() {
        Date newDate = Reader.readDate("Enter a new date: ");

        System.out.println(newDate);
    }

}
