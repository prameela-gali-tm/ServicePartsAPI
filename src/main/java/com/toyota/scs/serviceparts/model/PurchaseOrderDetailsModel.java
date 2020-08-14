package com.toyota.scs.serviceparts.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseOrderDetailsModel {

	@JsonProperty("poNumber")
	private String poNumber;
	
	@JsonProperty("orderType")
	private String orderType;
	
	@JsonProperty("deliveryDueDate")
	private Date deliveryDueDate;
	
	
	@JsonProperty("lineItemCount")
	private long lineItemCount;
	
	@JsonProperty("finalDestinationName")
	private String finalDestinationName;
	
	@JsonProperty("directShipFlag")
	private String directDhipFlag;

	public PurchaseOrderDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrderDetailsModel(String poNumber, String orderType, Date deliveryDueDate,String finalDestinationName, String directDhipFlag,
			long lineItemCount
			) {
		super();
		this.poNumber = poNumber;
		this.orderType = orderType;
		this.deliveryDueDate = deliveryDueDate;
		this.lineItemCount = lineItemCount;
		this.finalDestinationName = finalDestinationName;
		this.directDhipFlag = directDhipFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryDueDate == null) ? 0 : deliveryDueDate.hashCode());
		result = prime * result + ((directDhipFlag == null) ? 0 : directDhipFlag.hashCode());
		result = prime * result + ((finalDestinationName == null) ? 0 : finalDestinationName.hashCode());
		result = prime * result + (int) (lineItemCount ^ (lineItemCount >>> 32));
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
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
		PurchaseOrderDetailsModel other = (PurchaseOrderDetailsModel) obj;
		if (deliveryDueDate == null) {
			if (other.deliveryDueDate != null)
				return false;
		} else if (!deliveryDueDate.equals(other.deliveryDueDate))
			return false;
		if (directDhipFlag == null) {
			if (other.directDhipFlag != null)
				return false;
		} else if (!directDhipFlag.equals(other.directDhipFlag))
			return false;
		if (finalDestinationName == null) {
			if (other.finalDestinationName != null)
				return false;
		} else if (!finalDestinationName.equals(other.finalDestinationName))
			return false;
		if (lineItemCount != other.lineItemCount)
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		return true;
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

	public Date getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(Date deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public long getLineItemCount() {
		return lineItemCount;
	}

	public void setLineItemCount(long lineItemCount) {
		this.lineItemCount = lineItemCount;
	}

	public String getFinalDestinationName() {
		return finalDestinationName;
	}

	public void setFinalDestinationName(String finalDestinationName) {
		this.finalDestinationName = finalDestinationName;
	}

	public String getDirectDhipFlag() {
		return directDhipFlag;
	}

	public void setDirectDhipFlag(String directDhipFlag) {
		this.directDhipFlag = directDhipFlag;
	}
	
}
