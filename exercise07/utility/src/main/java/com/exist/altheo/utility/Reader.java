package com.exist.altheo.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

public class Reader {
    public static BufferedReader getReader() {
		BufferedReader reader= new BufferedReader (new InputStreamReader(System.in));
		return reader;
	}

    public static String readString(String message) {
		String input= null;
		try {
			System.out.print(message + ": ");
			input= StringUtils.strip(getReader().readLine());
		}
		catch(Exception e) {
		}
		return input;
	}
	
    public static int readInt(String message) {
        //ISSUE, this not working, value is null when wrong input then good input
        int input = 0;
		try {
			System.out.print(message + ": ");
			input= Integer.parseInt(getReader().readLine());
            return input;
		}
        catch(NumberFormatException  e){
            System.out.println("Must be an integer ");
            return readInt(message);
        }
		catch(Exception e) {
            System.out.println("Must be an integer ");
            return readInt(message);
		}
		
        
	}
	
	public static float readFloat(String message) {
		float input= (float) 0.00;
		try {
			System.out.print(message + " ");
			input= Float.parseFloat(getReader().readLine());
		}
		catch(Exception e) {
		}
		return input;
	}
	
	public static double readDouble(String message) {
		double input= 0.00;
		try {
			System.out.print(message + ": ");
			input= Double.parseDouble(getReader().readLine());
		}
		catch(Exception e) {
		}
		return input;
	}

	public static LocalDate readLocalDate(String message){
		int day = Reader.readInt("Enter day ");
		int month = Reader.readInt("Enter month ");
		int year = Reader.readInt("Enter year ");

		try{
			LocalDate date = LocalDate.of(year, month, day);
			return date;
		}catch(DateTimeException e){
			System.out.println("Invalid date please try again ");
			return readLocalDate(message);
		}
	}

	public static boolean readBoolean(String message) {
		String choice = Reader.readString(message+" [YES|NO]");
		
		if(choice.equals("YES"))
			return true;
		else if(choice.equals("NO"))
			return false;
		else
			System.out.println("Please enter [YES|NO] only ");
			return readBoolean(message);
	}
}
