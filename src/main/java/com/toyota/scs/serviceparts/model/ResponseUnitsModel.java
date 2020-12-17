package com.toyota.scs.serviceparts.model;

import java.util.List;

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
	
	@JsonProperty("poLineHomePosition")
	private String poLineHomePosition;
	
	@JsonProperty("poLineDeliveryDueDate")
	private String poLineDeliveryDueDate;
	
	@JsonProperty("partPOLineQuantityOrdered")
	private Integer partPOLineQuantityOrdered;
	
	@JsonProperty("partPOLineQuantityRemaining")
	private long partPOLineQuantityRemaining;
	
	@JsonProperty("partPOLineQuantityAllocated")
	private long partPOLineQuantityAllocated;
	
	@JsonProperty("partPOLineStatus")
	private String partPOLineStatus;
	
	@JsonProperty("dealerOrFinalDist")
	private String dealerOrFinalDist;
	
	@JsonProperty("serialNumberDetails")
	private List<String> serialNumberDetailsModel;
	
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

	public String getPoLineHomePosition() {
		return poLineHomePosition;
	}

	public void setPoLineHomePosition(String poLineHomePosition) {
		this.poLineHomePosition = poLineHomePosition;
	}

	public String getPoLineDeliveryDueDate() {
		return poLineDeliveryDueDate;
	}

	public void setPoLineDeliveryDueDate(String poLineDeliveryDueDate) {
		this.poLineDeliveryDueDate = poLineDeliveryDueDate;
	}

	public Integer getPartPOLineQuantityOrdered() {
		return partPOLineQuantityOrdered;
	}

	public void setPartPOLineQuantityOrdered(Integer partPOLineQuantityOrdered) {
		this.partPOLineQuantityOrdered = partPOLineQuantityOrdered;
	}

	public long getPartPOLineQuantityRemaining() {
		return partPOLineQuantityRemaining;
	}

	public void setPartPOLineQuantityRemaining(long partPOLineQuantityRemaining) {
		this.partPOLineQuantityRemaining = partPOLineQuantityRemaining;
	}

	public long getPartPOLineQuantityAllocated() {
		return partPOLineQuantityAllocated;
	}

	public void setPartPOLineQuantityAllocated(long partPOLineQuantityAllocated) {
		this.partPOLineQuantityAllocated = partPOLineQuantityAllocated;
	}

	public String getPartPOLineStatus() {
		return partPOLineStatus;
	}

	public void setPartPOLineStatus(String partPOLineStatus) {
		this.partPOLineStatus = partPOLineStatus;
	}
	
	public List<String> getSerialNumberDetailsModel() {
		return serialNumberDetailsModel;
	}

	public void setSerialNumberDetailsModel(List<String> serialNumberDetailsModel) {
		this.serialNumberDetailsModel = serialNumberDetailsModel;
	}

	
	public String getDealerOrFinalDist() {
		return dealerOrFinalDist;
	}

	public void setDealerOrFinalDist(String dealerOrFinalDist) {
		this.dealerOrFinalDist = dealerOrFinalDist;
	}

	
	public ResponseUnitsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUnitsModel(String partNumber, String poNumber, String poLineNumber, String poLineHomePosition,
			String poLineDeliveryDueDate, Integer partPOLineQuantityOrdered, long partPOLineQuantityRemaining,
			long partPOLineQuantityAllocated, String partPOLineStatus, String dealerOrFinalDist,
			List<String> serialNumberDetailsModel) {
		super();
		this.partNumber = partNumber;
		this.poNumber = poNumber;
		this.poLineNumber = poLineNumber;
		this.poLineHomePosition = poLineHomePosition;
		this.poLineDeliveryDueDate = poLineDeliveryDueDate;
		this.partPOLineQuantityOrdered = partPOLineQuantityOrdered;
		this.partPOLineQuantityRemaining = partPOLineQuantityRemaining;
		this.partPOLineQuantityAllocated = partPOLineQuantityAllocated;
		this.partPOLineStatus = partPOLineStatus;
		this.dealerOrFinalDist = dealerOrFinalDist;
		this.serialNumberDetailsModel = serialNumberDetailsModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dealerOrFinalDist == null) ? 0 : dealerOrFinalDist.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + (int) (partPOLineQuantityAllocated ^ (partPOLineQuantityAllocated >>> 32));
		result = prime * result + ((partPOLineQuantityOrdered == null) ? 0 : partPOLineQuantityOrdered.hashCode());
		result = prime * result + (int) (partPOLineQuantityRemaining ^ (partPOLineQuantityRemaining >>> 32));
		result = prime * result + ((partPOLineStatus == null) ? 0 : partPOLineStatus.hashCode());
		result = prime * result + ((poLineDeliveryDueDate == null) ? 0 : poLineDeliveryDueDate.hashCode());
		result = prime * result + ((poLineHomePosition == null) ? 0 : poLineHomePosition.hashCode());
		result = prime * result + ((poLineNumber == null) ? 0 : poLineNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + ((serialNumberDetailsModel == null) ? 0 : serialNumberDetailsModel.hashCode());
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
		if (dealerOrFinalDist == null) {
			if (other.dealerOrFinalDist != null)
				return false;
		} else if (!dealerOrFinalDist.equals(other.dealerOrFinalDist))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (partPOLineQuantityAllocated != other.partPOLineQuantityAllocated)
			return false;
		if (partPOLineQuantityOrdered == null) {
			if (other.partPOLineQuantityOrdered != null)
				return false;
		} else if (!partPOLineQuantityOrdered.equals(other.partPOLineQuantityOrdered))
			return false;
		if (partPOLineQuantityRemaining != other.partPOLineQuantityRemaining)
			return false;
		if (partPOLineStatus == null) {
			if (other.partPOLineStatus != null)
				return false;
		} else if (!partPOLineStatus.equals(other.partPOLineStatus))
			return false;
		if (poLineDeliveryDueDate == null) {
			if (other.poLineDeliveryDueDate != null)
				return false;
		} else if (!poLineDeliveryDueDate.equals(other.poLineDeliveryDueDate))
			return false;
		if (poLineHomePosition == null) {
			if (other.poLineHomePosition != null)
				return false;
		} else if (!poLineHomePosition.equals(other.poLineHomePosition))
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
		if (serialNumberDetailsModel == null) {
			if (other.serialNumberDetailsModel != null)
				return false;
		} else if (!serialNumberDetailsModel.equals(other.serialNumberDetailsModel))
			return false;
		return true;
	}
	
	
	
	
}
