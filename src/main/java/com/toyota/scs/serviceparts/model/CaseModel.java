package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "cases")
public class CaseModel {

	@JsonProperty("caseNumber")
	private String caseNumber;
	
	@JsonProperty("units")
	private List<UnitsModel> units = new ArrayList<UnitsModel>();
	
	@JsonProperty("rfidDetails")
	private List<RfidDetailsModel> rfidDetails = new ArrayList<RfidDetailsModel>();
	
	@JsonProperty("exceptions")
	private List<ExceptionsModel> exceptions=new ArrayList<ExceptionsModel>();
	
	
	
	
	

	public CaseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseModel(String caseNumber, List<UnitsModel> units, List<RfidDetailsModel> rfidDetails,
			List<ExceptionsModel> exceptions) {
		super();
		this.caseNumber = caseNumber;
		this.units = units;
		this.rfidDetails = rfidDetails;
		this.exceptions = exceptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
		result = prime * result + ((exceptions == null) ? 0 : exceptions.hashCode());
		result = prime * result + ((rfidDetails == null) ? 0 : rfidDetails.hashCode());
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
		CaseModel other = (CaseModel) obj;
		if (caseNumber == null) {
			if (other.caseNumber != null)
				return false;
		} else if (!caseNumber.equals(other.caseNumber))
			return false;
		if (exceptions == null) {
			if (other.exceptions != null)
				return false;
		} else if (!exceptions.equals(other.exceptions))
			return false;
		if (rfidDetails == null) {
			if (other.rfidDetails != null)
				return false;
		} else if (!rfidDetails.equals(other.rfidDetails))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public List<UnitsModel> getUnits() {
		return units;
	}

	public void setUnits(List<UnitsModel> units) {
		this.units = units;
	}

	public List<RfidDetailsModel> getRfidDetails() {
		return rfidDetails;
	}

	public void setRfidDetails(List<RfidDetailsModel> rfidDetails) {
		this.rfidDetails = rfidDetails;
	}

	public List<ExceptionsModel> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<ExceptionsModel> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public String toString() {
		return "CaseModel [caseNumber=" + caseNumber + ", units=" + units + ", rfidDetails=" + rfidDetails
				+ ", exceptions=" + exceptions + "]";
	}
	
}
