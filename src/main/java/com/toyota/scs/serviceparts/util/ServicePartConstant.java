package com.toyota.scs.serviceparts.util;

public class ServicePartConstant {

	//Case Build Vendor Level

	public static String VENDOR_CODE_INVALID = "Vendor Code must be 5 digits.";
	public static String VENDOR_CODE_EMPTY= "Vendor Code is required field.";
	public static String VENDOR_CODE_SPECIAL_CHAR = "Vendor Code should not contains any special characters, it will allows only Alpha Numneric values.";
	public static String VENDOR_CODE_DOES_NOT_EXIST= "Vendor Code does not exist in DataBase.";
	public static  String RFID_INVALID = "RFID number must be 12 digits.";

	public static  String CASE_NUMBER_INVALID = "Case Number must be 8 digits.  Also, it must not be used in last 60 days. ";
	public static  String CASE_NUMBER_EMPTY = "Case Number is required field.";
	public static String CASE_NUMBER_NUMBERIC="Case Number allows only Numeric values";
	//Case Build Unit Level

	public static  String PART_NUMBER_INVALID = "Part Number must be 12 digits or less. ";
	public static  String PART_NUMBER_EMPTY = "Part Number is required field.";
	public static  String PO_NUMBER_INVALID = "PO Number must be 8 digits.";
	public static  String PO_NUMBER_EMPTY = "PO Number is required field.";
	public static  String PO_LINE_NUMBER_INVALID = "PO Line Number must be 5 digits or less.";
	public static  String PO_LINE_NUMBER_EMPTY = "PO Line Number is required field.";
	public static  String PART_QUANTITY_INVALID = "Part Quantity must be numeric and 5 digits or less. ";
	public static  String PART_QUANTITY_EMPTY = "Part Quantity is required field.";
	public static  String HOMEPOSITION_INVALID = "Home Position must be 1 digit.";
	public static  String DELIVERY_DUEDATE_INVALID = "Delivery Due Date Time field not in required date time format yyyy-MM-dd.";
	public static  String CONTAINER_ID_INVALID = "Container ID must be 12 digits or less. ";
	public static  String SERIAL_NUMBER_INVALID = "Serial Number must be 20 digits or less. ";
	public static  String SUB_PARTNUMBER_INVALID = "Substitution Part Number must be 12 digits or less. ";

	//Shipment Load Trailer Level

	public static  String TRAILER_NUMBER_INVALID = "Trailer Number must be 30 digits or less.";
	public static  String TRAILER_NUMBER_EMPTY = "Trailer Number is required field.";
	public static  String ROUTE_INVALID = "Route must be 7 digits or less. ";
	public static  String RUN_INVALID = "Run must be 2 digits or less. ";
	public static  String PLAN_PICKUPDATE_INVALID = "Planned Pickup Date field not in required date time format yyyy-MM-ddTHH:mm. "; 
	public static  String SEALNUMBER_INVALID = "Seal Number be 20 digits or less.";
	public static  String SUPPLIER_TEAM_FIRSTNAME_INVALID = "Supplier Team Member First Name must be 50 digits or less.";
	public static  String SUPPLIER_TEAM_LASTNAME_INVALID = "Supplier Team Member Last Name must be 50 digits or less.";
	public static  String LPCPDE_INVALID = "Logistics Partner (SCAC) code must be 6 digits or less.";
	public static  String DRIVER_TEAM_FIRSTNAME_INVALID = "Driver Team First Name must be 50 digits or less. ";
	public static  String DRIVER_TEAM_LASTNAME_INVALID = "Driver Team Last Name must be 50 digits or less. ";

	//Shipment Load Vendor Level
	public static  String SUPPLIER_SHIPMENTID_INVALID = "Shipment ID must be 15 digits or less.  Also, it must not be used in last 12 months. ";
	public static  String SUPPLIER_SHIPMENTID_EMPTY = "Shipment ID is required field.";

	//Shipment Load Case Level
	
	
	public static String ORDER_NOT_FOUND="Order not found";
	public static String PART_LINE_DDD="Part number, Line iteam and Delivery due date does not match with PoNumber,vendor code and order type";
	public static String PART_LINE_DDD_INVALID ="Part number, Line iteam and Delivery due date record does not exist in Data base";
}
