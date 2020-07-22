package com.toyota.scs.serviceparts.util;

public class CommonValidation {
	
	public static boolean isAlphaNumeric(String s){
	    String pattern= "^[a-zA-Z0-9]*$";
	    return s.matches(pattern);
	}
	public static boolean isNumeric(String i){
	    String pat= "^[0-9]*$";
	    return i.matches(pat);
	}
}
