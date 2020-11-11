package com.toyota.scs.serviceparts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewPartDetailsModel {

	@JsonProperty("partNumber")
	private String partNumber;
	
	@JsonProperty("homePosition")
	private String homePosition;
	
	@JsonProperty("dealerOrDistinationFD")
	private String dealerOrDistinationFD;

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getHomePosition() {
		return homePosition;
	}

	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}

	public String getDealerOrDistinationFD() {
		return dealerOrDistinationFD;
	}

	public void setDealerOrDistinationFD(String dealerOrDistinationFD) {
		this.dealerOrDistinationFD = dealerOrDistinationFD;
	}

	public ViewPartDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewPartDetailsModel(String partNumber, String homePosition, String dealerOrDistinationFD) {
		super();
		this.partNumber = partNumber;
		this.homePosition = homePosition;
		this.dealerOrDistinationFD = dealerOrDistinationFD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dealerOrDistinationFD == null) ? 0 : dealerOrDistinationFD.hashCode());
		result = prime * result + ((homePosition == null) ? 0 : homePosition.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
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
		ViewPartDetailsModel other = (ViewPartDetailsModel) obj;
		if (dealerOrDistinationFD == null) {
			if (other.dealerOrDistinationFD != null)
				return false;
		} else if (!dealerOrDistinationFD.equals(other.dealerOrDistinationFD))
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
		return true;
	}
	
	
	
}
