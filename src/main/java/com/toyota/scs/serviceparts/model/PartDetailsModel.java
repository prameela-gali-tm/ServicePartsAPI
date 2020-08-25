package com.toyota.scs.serviceparts.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartDetailsModel {

	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("deliveryDueDate")
	private Date deliveryDueDate;
	
	@JsonProperty("orderQuantity")
	private long orderQuantity;
	
	@JsonProperty("outstandingQuantity")
	private long outstandingQuantity;
	
	@JsonProperty("poNumber")
	private String poNumber;
	
	@JsonProperty("orderType")
	private String orderType;
	
	@JsonProperty("vendorCode")
	private String vendorCode;
	
	@JsonProperty("lineItemNumber")
	private String lineItemNumber;

	public PartDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartDetailsModel(String partNumber, Date deliveryDueDate, long orderQuantity, long outstandingQuantity,
			String poNumber, String orderType, String vendorCode,String lineItemNumber) {
		super();
		this.partNumber = partNumber;
		this.deliveryDueDate = deliveryDueDate;
		this.orderQuantity = orderQuantity;
		this.outstandingQuantity = outstandingQuantity;
		this.poNumber = poNumber;
		this.orderType = orderType;
		this.vendorCode = vendorCode;
		this.lineItemNumber=lineItemNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Date getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(Date deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public long getOutstandingQuantity() {
		return outstandingQuantity;
	}

	public void setOutstandingQuantity(long outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getLineItemNumber() {
		return lineItemNumber;
	}

	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	
	
	
	
}
