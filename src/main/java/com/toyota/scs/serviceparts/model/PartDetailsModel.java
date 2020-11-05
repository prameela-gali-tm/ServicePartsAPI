package com.toyota.scs.serviceparts.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartDetailsModel {

	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("deliveryDueDate")
	private String deliveryDueDate;
	
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

	@JsonProperty("homePosition")
	private String homePosition;
	
	@JsonProperty("distinationFD")
	private String distinationFD;
	
	@JsonIgnore
	private long partId;
	@JsonIgnore
	private long orderId;
	@JsonIgnore
	private String partialStatus;
	@JsonIgnore
	private long supplierFullFillQuantity;
	
	@JsonIgnore
	private String containerID;
	@JsonIgnore
	private String dealer;
	@JsonIgnore
	private String directShip;
	@JsonIgnore
	private String vendorPartNumber;
	@JsonIgnore
	private String orderRefNumber;
	@JsonIgnore
	private String serialNumber;
	@JsonIgnore
	private Long subPartNumber;
	@JsonIgnore
	private String partDesc;
	
	@JsonIgnore
	private List<String> serialNumberDetailsModel;
	
	public PartDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartDetailsModel(String partNumber, String deliveryDueDate, long orderQuantity, long outstandingQuantity,
			String poNumber, String orderType, String vendorCode,String lineItemNumber,String homePosition,String distinationFD) {
		super();
		this.partNumber = partNumber;
		this.deliveryDueDate = deliveryDueDate;
		this.orderQuantity = orderQuantity;
		this.outstandingQuantity = outstandingQuantity;
		this.poNumber = poNumber;
		this.orderType = orderType;
		this.vendorCode = vendorCode;
		this.lineItemNumber=lineItemNumber;
		this.homePosition = homePosition;
		this.distinationFD = distinationFD;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(String deliveryDueDate) {
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

	public String getHomePosition() {
		return homePosition;
	}

	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPartialStatus() {
		return partialStatus;
	}

	public void setPartialStatus(String partialStatus) {
		this.partialStatus = partialStatus;
	}

	public long getSupplierFullFillQuantity() {
		return supplierFullFillQuantity;
	}

	public void setSupplierFullFillQuantity(long supplierFullFillQuantity) {
		this.supplierFullFillQuantity = supplierFullFillQuantity;
	}

	public String getDistinationFD() {
		return distinationFD;
	}

	public void setDistinationFD(String distinationFD) {
		this.distinationFD = distinationFD;
	}

	public String getContainerID() {
		return containerID;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getDirectShip() {
		return directShip;
	}

	public void setDirectShip(String directShip) {
		this.directShip = directShip;
	}

	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}

	public String getOrderRefNumber() {
		return orderRefNumber;
	}

	public void setOrderRefNumber(String orderRefNumber) {
		this.orderRefNumber = orderRefNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	

	public Long getSubPartNumber() {
		return subPartNumber;
	}

	public void setSubPartNumber(Long subPartNumber) {
		this.subPartNumber = subPartNumber;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public List<String> getSerialNumberDetailsModel() {
		return serialNumberDetailsModel;
	}

	public void setSerialNumberDetailsModel(List<String> serialNumberDetailsModel) {
		this.serialNumberDetailsModel = serialNumberDetailsModel;
	}
}
