package com.toyota.scs.serviceparts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewPONumberDetailModel {

	@JsonProperty("poNumber")
	private String poNumber;
	
	@JsonProperty("deliveryDueDate")
	private String deliveryDueDate;
	
	@JsonProperty("unFulFilledQuantity")
	private long unFulFilledQuantity;
	
	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("status")
	private String status;

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(String deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public long getUnFulFilledQuantity() {
		return unFulFilledQuantity;
	}

	public void setUnFulFilledQuantity(long unFulFilledQuantity) {
		this.unFulFilledQuantity = unFulFilledQuantity;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ViewPONumberDetailModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewPONumberDetailModel(String poNumber, String deliveryDueDate, long unFulFilledQuantity, String partNumber,
			String status) {
		super();
		this.poNumber = poNumber;
		this.deliveryDueDate = deliveryDueDate;
		this.unFulFilledQuantity = unFulFilledQuantity;
		this.partNumber = partNumber;
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryDueDate == null) ? 0 : deliveryDueDate.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + (int) (unFulFilledQuantity ^ (unFulFilledQuantity >>> 32));
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
		ViewPONumberDetailModel other = (ViewPONumberDetailModel) obj;
		if (deliveryDueDate == null) {
			if (other.deliveryDueDate != null)
				return false;
		} else if (!deliveryDueDate.equals(other.deliveryDueDate))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (unFulFilledQuantity != other.unFulFilledQuantity)
			return false;
		return true;
	}
	
	
}
