package com.exist.altheo;

import java.time.LocalDate;
import java.util.Date;

import com.exist.altheo.utility.Reader;

public class App 
{
    public static void main( String[] args )
    {
        //TODO - CALL THE SERVICE CONTROLLER HERE
        LocalDate newDate = Reader.readLocalDate("Enter date hired ");

        System.out.println(newDate);
    }
}
