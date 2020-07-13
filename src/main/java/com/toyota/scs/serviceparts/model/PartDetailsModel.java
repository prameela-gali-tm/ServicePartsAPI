package com.toyota.scs.serviceparts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class PartDetailsModel {

	private long orderid;
	private String poNumber;
	private String orderType;
	private String vendorCode;
	private long partId;
	private String partNumber;
	private String lineItemNumber;
	private Date DDD;
	private String partDesc;
	private long orderQuantity;
	private String vendorPartNumber;
	private String directShip;
	private String homePosition;
	private Date transmissionDate;
	private String orderRefNumber;
	private String dealer;
	private String status;
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
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
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getLineItemNumber() {
		return lineItemNumber;
	}
	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}
	public Date getDDD() {
		return DDD;
	}
	public void setDDD(Date dDD) {
		DDD = dDD;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public long getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getVendorPartNumber() {
		return vendorPartNumber;
	}
	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}
	public String getDirectShip() {
		return directShip;
	}
	public void setDirectShip(String directShip) {
		this.directShip = directShip;
	}
	public String getHomePosition() {
		return homePosition;
	}
	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}
	public Date getTransmissionDate() {
		return transmissionDate;
	}
	public void setTransmissionDate(Date transmissionDate) {
		this.transmissionDate = transmissionDate;
	}
	public String getOrderRefNumber() {
		return orderRefNumber;
	}
	public void setOrderRefNumber(String orderRefNumber) {
		this.orderRefNumber = orderRefNumber;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public PartDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
