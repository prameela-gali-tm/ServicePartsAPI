package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
@XmlRootElement(name = "cases")
public class ResponseCaseModel {

	@JsonProperty("caseNumber")
	private String caseNumber;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("transportationCode")
	private int transportationCode;
	
	@JsonProperty("directShipFlag")
	private String directShipFlag;
	
	@JsonProperty("units")
	private List<ResponseUnitsModel> units = new ArrayList<ResponseUnitsModel>();

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public List<ResponseUnitsModel> getUnits() {
		return units;
	}

	public void setUnits(List<ResponseUnitsModel> units) {
		this.units = units;
	}	

	
	public ResponseCaseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseCaseModel(String caseNumber,List<ResponseUnitsModel> units) {
		super();
		this.caseNumber = caseNumber;
		this.units = units;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
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
		ResponseCaseModel other = (ResponseCaseModel) obj;
		if (caseNumber == null) {
			if (other.caseNumber != null)
				return false;
		} else if (!caseNumber.equals(other.caseNumber))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTransportationCode() {
		return transportationCode;
	}

	public void setTransportationCode(int transportationCode) {
		this.transportationCode = transportationCode;
	}

	public String getDirectShipFlag() {
		return directShipFlag;
	}

	public void setDirectShipFlag(String directShipFlag) {
		this.directShipFlag = directShipFlag;
	}
	
	
	
	
}
