package com.exist.altheo.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
			System.out.print(message + " ");
			input= Double.parseDouble(getReader().readLine());
		}
		catch(Exception e) {
		}
		return input;
	}
}
