package com.toyota.scs.serviceparts.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
@XmlRootElement(name = "units")
public class ResponseUnitsModel {

	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("poNumber")
	private String poNumber;
	
	@JsonProperty("poLineNumber")
	private String poLineNumber;
	
	@JsonProperty("homePosition")
	private String homePosition;
	
	@JsonProperty("deliveryDueDate")
	private String deliveryDueDate;
	
	@JsonProperty("partQuantity")
	private Integer plannedOrderQuantity;
	
	@JsonProperty("outstandingOrderQuantity")
	private long outstandingOrderQuantity;
	
	@JsonProperty("supplierOrderQuantityFullFilled")
	private long supplierOrderQuantityFullFilled;
	
	@JsonProperty("orderStatus")
	private String orderStatus;

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoLineNumber() {
		return poLineNumber;
	}

	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}

	public String getHomePosition() {
		return homePosition;
	}

	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}

	public String getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(String deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public Integer getPlannedOrderQuantity() {
		return plannedOrderQuantity;
	}

	public void setPlannedOrderQuantity(Integer plannedOrderQuantity) {
		this.plannedOrderQuantity = plannedOrderQuantity;
	}

	public long getOutstandingOrderQuantity() {
		return outstandingOrderQuantity;
	}

	public void setOutstandingOrderQuantity(long outstandingOrderQuantity) {
		this.outstandingOrderQuantity = outstandingOrderQuantity;
	}

	public long getSupplierOrderQuantityFullFilled() {
		return supplierOrderQuantityFullFilled;
	}

	public void setSupplierOrderQuantityFullFilled(long supplierOrderQuantityFullFilled) {
		this.supplierOrderQuantityFullFilled = supplierOrderQuantityFullFilled;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public ResponseUnitsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUnitsModel(String partNumber, String poNumber, String poLineNumber, String homePosition,
			String deliveryDueDate, Integer plannedOrderQuantity, long outstandingOrderQuantity,
			long supplierOrderQuantityFullFilled, String orderStatus) {
		super();
		this.partNumber = partNumber;
		this.poNumber = poNumber;
		this.poLineNumber = poLineNumber;
		this.homePosition = homePosition;
		this.deliveryDueDate = deliveryDueDate;
		this.plannedOrderQuantity = plannedOrderQuantity;
		this.outstandingOrderQuantity = outstandingOrderQuantity;
		this.supplierOrderQuantityFullFilled = supplierOrderQuantityFullFilled;
		this.orderStatus = orderStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryDueDate == null) ? 0 : deliveryDueDate.hashCode());
		result = prime * result + ((homePosition == null) ? 0 : homePosition.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + (int) (outstandingOrderQuantity ^ (outstandingOrderQuantity >>> 32));
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((plannedOrderQuantity == null) ? 0 : plannedOrderQuantity.hashCode());
		result = prime * result + ((poLineNumber == null) ? 0 : poLineNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + (int) (supplierOrderQuantityFullFilled ^ (supplierOrderQuantityFullFilled >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseUnitsModel other = (ResponseUnitsModel) obj;
		if (deliveryDueDate == null) {
			if (other.deliveryDueDate != null)
				return false;
		} else if (!deliveryDueDate.equals(other.deliveryDueDate))
			return false;
		if (homePosition == null) {
			if (other.homePosition != null)
				return false;
		} else if (!homePosition.equals(other.homePosition))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (outstandingOrderQuantity != other.outstandingOrderQuantity)
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (plannedOrderQuantity == null) {
			if (other.plannedOrderQuantity != null)
				return false;
		} else if (!plannedOrderQuantity.equals(other.plannedOrderQuantity))
			return false;
		if (poLineNumber == null) {
			if (other.poLineNumber != null)
				return false;
		} else if (!poLineNumber.equals(other.poLineNumber))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		if (supplierOrderQuantityFullFilled != other.supplierOrderQuantityFullFilled)
			return false;
		return true;
	}
	
	
}
