package com.toyota.scs.serviceparts.util;

public class Test {

	public static void main(String[] args) {
		Validation validation = new Validation();
		System.out.println(validation.parseTimestamp("2020-06-01T11:25"));
	}
}
