package com.toyota.scs.serviceparts.util;

public class ServicePartConstant {

	//Case Build Vendor Level

	public static String VENDOR_CODE_INVALID = "Vendor Code must be 5 digits.";
	public static String SHIPMENT_ID_INVALID = "Shipment Id must be less than 15 digit";
	public static String TRAILER_NUM_INVALID = "Trailer Number must be less than 30 digit";
	public static String SEAL_NUMBER_INVALID = "Seal Number must be less than 20 digit";
	public static String SUPPLIER_LAST_NAME_INVALID = "Supplier Last Name must be less than 50 digit";
	public static String SUPPLIER_FIRST_NAME_INVALID = "Supplier First Name must be less than 50 digit";
	public static String DRIVER_FIRST_NAME_INVALID = "Driver First Name must be less than 50 digit";
	public static String DRIVER_LAST_NAME_INVALID = "Driver Last Name must be less than 50 digit";
	public static String LPCODE_INVALID = "LP Code must be less than 6 digit";

	public static String VENDOR_CODE_EMPTY = "Vendor Code is required field.";
	public static String SHIPMENT_ID_EMPTY = "Shipment ID is required field.";
	public static String TRAILER_NUM_EMPTY = "Trailer Number is required field.";
	public static String LPCODE_EMPTY = "LP Code is required field.";
	public static String VENDOR_CODE_SPECIAL_CHAR = "Vendor Code should not contains any special characters, it will allows only Alpha Numneric values.";
	public static String VENDOR_CODE_DOES_NOT_EXIST= "Vendor Code does not exist in DataBase.";
	public static  String RFID_INVALID = "RFID number must be 12 digits.";
	public static String RFID_SPC = "No special character are allowed in RFID.";

	public static String CASE_NUMBER_INVALID = "Case Number must be 8 digits.Also, it must not be used in last 60 days. ";
	public static String CASE_NUMBER_EMPTY = "Case Number is required field.";
	public static String CASE_NUMBER_NOT_EXISTS = "Case Number doesnot exist";
	public static String CASE_NUMBER_NUMBERIC = "Case Number allows only Numeric values";
	public static String SHIPMENT_ID_NUMERIC = "Shipment Id allows only Numeric values";
	public static String TRAILER_NUM_SPEC = "No special character are allowed in Trailer Number";
	public static String SEAL_NUMBER_SPEC = "No special character are allowed in Seal Number";
	public static String LPCODE_SPEC = "No special character are allowed in LP Code";
	public static String ROUTE_SPEC = "No special character are allowed in Route";
	public static String RUN_SPEC = "No special character are allowed in Run";
	public static String SUPPLIER_LAST_NAME_SPEC = "No special character are allowed in Supplier Last Name";
	public static String SUPPLIER_FIRST_NAME_SPEC = "No special character are allowed in Supplier First Name";
	public static String DRIVER_FIRST_NAME_SPEC = "No special character are allowed in Driver First Name";
	public static String DRIVER_LAST_NAME_SPEC = "No special character are allowed in Driver Last Name";
	public static String CASE_NUMBER_SPEC="No special character are allowed in Case Number";

	// Case Build Unit Level

	public static String PART_NUMBER_INVALID = "Part Number must be 20 digits or less. ";
	public static String PART_NUMBER_EMPTY = "Part Number is required field.";
	public static String PART_NUMBER_SPEC = "No special character are allowed in Part Number";
	public static String PO_NUMBER_INVALID = "PO Number must be 8 digits.";
	public static String PO_NUMBER_SPEC = "No special character are allowed in PO Number";
	public static String PO_NUMBER_EMPTY = "PO Number is required field.";
	public static String PO_LINE_NUMBER_INVALID = "PO Line Number must be 5 digits or less.";
	public static String PO_LINE_NUMBER_SPEC = "No special character are allowed in PO Line Item Number";
	public static String PO_LINE_NUMBER_EMPTY = "PO Line Number is required field.";
	public static String PART_QUANTITY_INVALID = "Part Quantity must be numeric and 5 digits or less. ";
	public static String PART_QUANTITY_EMPTY = "Part Quantity is required field.";
	public static String HOMEPOSITION_INVALID = "Home Position must be 1 digit.";
	public static String HOMEPOSTION_SPEC = "No special character are allowed in Home Position";
	public static String DELIVERY_DUEDATE_INVALID = "Delivery Due Date Time field not in required date time format yyyy-MM-dd.";
	public static String PLAN_PICKUP_DATE_INVALID = "Plan Pickup date time field not in required date time format yyyy-MM-dd.";
	public static String CONTAINER_ID_INVALID = "Container ID must be 12 digits or less. ";
	public static String SERIAL_NUMBER_INVALID = "Serial Number must be 20 digits or less. ";
	public static String SERIAL_NUMBWE_SPEC = "No special character are allowed in Serail Number";
	public static String SUB_PARTNUMBER_INVALID = "Substitution Part Number must be 12 digits or less. ";
	public static String SUB_PARTNUMBER_SPEC = "Substitution Part Number does not allow special characters";

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
	
	
	public static String ORDER_NOT_FOUND="Order not found for the given Vendor Code and Po Number";
	public static String PART_LINE_DDD="Part number, Line iteam and Delivery due date does not match with PoNumber,vendor code and order type";
	public static String PART_LINE_DDD_INVALID ="Part number, Line iteam and Delivery due date record does not exist in Data base";
	
	public static String ORDER_QUANTITY="Part Order Quantity is greater than planned Order Qunatity";
	public static String DUPLICATE_UNITS ="Duplicate record exist under the same case number";
	public static String EXCEPTION_CODE ="No special character are allowed in Exception Code.";
	public static String SERVICE_INVALID = "Service number count does not match with give part order qunatity ";
	public static String DUPLICATE_SERIAL_NUMBER ="Duplicate serial number present in given payload.";
	public static String DEALER_CODE = "Dealer code is required when direct shipment flag is Y";
	public static String RECORD_DOES_NOT_EXIST = "Record does not present for the given criteria";
	public static String DIRECT_FD="Distination FD is required when the direct shioment flag is N";
	public static String DEALER_CODE_LEN = "Dealer code must be 10 digits or less";
	public static String DIRECT_FD_LEN = "Dist FD code must be 30 digits or less";
	public static String NO_RECORDS=" | combination, no record found for given vendor code, case number, direct shipment flag, transportation code, dealer number and distFD combination ";
	public static String FULL_FILLED_STATUS="FULL FILLED";
	public static String PARTIAL_STATUS="PARTIAL";
	public static String DRAFT_STATUS = "DRAFT";
	public static String INSERT_STATUS ="INSERT";
	public static String CASE_BUILD_STATUS="CASE_BUILD";
	public static String SUBMIT_STATUS="SUBMIT";
	public static String CASE_NUMBER_DOES_NOT_EXIST="No Record found for given case number ";
	public static String DUPLICATE_CASENUMBER = "Duplicate instance of Case Found in payload";
	public static String QTY1= "combination submitted qty greater than available qty ";
	public static String QTY2= " for PO Number, PO Line, Part Number combination ";
	public static String SYSTEM="SYSTEM";
	
}
