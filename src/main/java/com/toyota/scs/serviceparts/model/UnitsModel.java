package com.toyota.scs.serviceparts.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
@XmlRootElement(name = "units")
public class UnitsModel {

	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("poNumber")
	private String poNumber;
	
	@JsonProperty("poLineNumber")
	private String poLineNumber;
	
	@JsonProperty("partQuantity")
	private Integer partQuantity;
	
	@JsonProperty("homePosition")
	private String homePosition;
	
	@JsonProperty("deliveryDueDate")
	private String deliveryDueDate;
	
	@JsonProperty("containerID")
	private String containerID;
	
	@JsonProperty("serialNumber")
	private String serialNumber;
	
	@JsonProperty("subPartNumber")
	private String subPartNumber;

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

	public Integer getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Integer partQuantity) {
		this.partQuantity = partQuantity;
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

	public String getContainerID() {
		return containerID;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSubPartNumber() {
		return subPartNumber;
	}

	public void setSubPartNumber(String subPartNumber) {
		this.subPartNumber = subPartNumber;
	}
	
	public UnitsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnitsModel(String partNumber, String poNumber, String poLineNumber, Integer partQuantity,
			String homePosition, String deliveryDueDate, String containerID, String serialNumber,
			String subPartNumber) {
		super();
		this.partNumber = partNumber;
		this.poNumber = poNumber;
		this.poLineNumber = poLineNumber;
		this.partQuantity = partQuantity;
		this.homePosition = homePosition;
		this.deliveryDueDate = deliveryDueDate;
		this.containerID = containerID;
		this.serialNumber = serialNumber;
		this.subPartNumber = subPartNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containerID == null) ? 0 : containerID.hashCode());
		result = prime * result + ((deliveryDueDate == null) ? 0 : deliveryDueDate.hashCode());
		result = prime * result + ((homePosition == null) ? 0 : homePosition.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((partQuantity == null) ? 0 : partQuantity.hashCode());
		result = prime * result + ((poLineNumber == null) ? 0 : poLineNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
		result = prime * result + ((subPartNumber == null) ? 0 : subPartNumber.hashCode());
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
		UnitsModel other = (UnitsModel) obj;
		if (containerID == null) {
			if (other.containerID != null)
				return false;
		} else if (!containerID.equals(other.containerID))
			return false;
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
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (partQuantity == null) {
			if (other.partQuantity != null)
				return false;
		} else if (!partQuantity.equals(other.partQuantity))
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
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (subPartNumber == null) {
			if (other.subPartNumber != null)
				return false;
		} else if (!subPartNumber.equals(other.subPartNumber))
			return false;
		return true;
	}
	
	
	
}
