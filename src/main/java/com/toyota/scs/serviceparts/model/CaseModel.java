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
	
	@JsonProperty("transportationCode")
	private int transportationCode;
	
	@JsonProperty("directShipFlag")
	private String directShipFlag;

	@JsonProperty("distFD")
	private String distFD;
	
	@JsonProperty("dealerNumber")
	private String dealerNumber;
	
	@JsonProperty("rfidDetails")
	private List<RfidDetailsModel> rfidDetails = new ArrayList<RfidDetailsModel>();

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

	public String getDistFD() {
		return distFD;
	}

	public void setDistFD(String distFD) {
		this.distFD = distFD;
	}

	public String getDealerNumber() {
		return dealerNumber;
	}

	public void setDealerNumber(String dealerNumber) {
		this.dealerNumber = dealerNumber;
	}

	public CaseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseModel(String caseNumber, List<UnitsModel> units, int transportationCode, String directShipFlag,
			String distFD, String dealerNumber, List<RfidDetailsModel> rfidDetails) {
		super();
		this.caseNumber = caseNumber;
		this.units = units;
		this.transportationCode = transportationCode;
		this.directShipFlag = directShipFlag;
		this.distFD = distFD;
		this.dealerNumber = dealerNumber;
		this.rfidDetails = rfidDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseNumber == null) ? 0 : caseNumber.hashCode());
		result = prime * result + ((dealerNumber == null) ? 0 : dealerNumber.hashCode());
		result = prime * result + ((directShipFlag == null) ? 0 : directShipFlag.hashCode());
		result = prime * result + ((distFD == null) ? 0 : distFD.hashCode());
		result = prime * result + ((rfidDetails == null) ? 0 : rfidDetails.hashCode());
		result = prime * result + transportationCode;
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
		if (dealerNumber == null) {
			if (other.dealerNumber != null)
				return false;
		} else if (!dealerNumber.equals(other.dealerNumber))
			return false;
		if (directShipFlag == null) {
			if (other.directShipFlag != null)
				return false;
		} else if (!directShipFlag.equals(other.directShipFlag))
			return false;
		if (distFD == null) {
			if (other.distFD != null)
				return false;
		} else if (!distFD.equals(other.distFD))
			return false;
		if (rfidDetails == null) {
			if (other.rfidDetails != null)
				return false;
		} else if (!rfidDetails.equals(other.rfidDetails))
			return false;
		if (transportationCode != other.transportationCode)
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	
	
	
}
