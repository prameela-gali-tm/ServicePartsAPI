package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VendorsModel {
	@JsonProperty("vendorCode")
	private String vendorCode;
	@JsonProperty("shipmentID")
	private String shipmentID;
	@JsonProperty("cases")
	private List<CaseModel> caseModel=new ArrayList<CaseModel>();
	public VendorsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VendorsModel(String vendorCode, String shipmentID, List<CaseModel> caseModel) {
		super();
		this.vendorCode = vendorCode;
		this.shipmentID = shipmentID;
		this.caseModel = caseModel;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}
	public List<CaseModel> getCaseModel() {
		return caseModel;
	}
	public void setCaseModel(List<CaseModel> caseModel) {
		this.caseModel = caseModel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caseModel == null) ? 0 : caseModel.hashCode());
		result = prime * result + ((shipmentID == null) ? 0 : shipmentID.hashCode());
		result = prime * result + ((vendorCode == null) ? 0 : vendorCode.hashCode());
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
		VendorsModel other = (VendorsModel) obj;
		if (caseModel == null) {
			if (other.caseModel != null)
				return false;
		} else if (!caseModel.equals(other.caseModel))
			return false;
		if (shipmentID == null) {
			if (other.shipmentID != null)
				return false;
		} else if (!shipmentID.equals(other.shipmentID))
			return false;
		if (vendorCode == null) {
			if (other.vendorCode != null)
				return false;
		} else if (!vendorCode.equals(other.vendorCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "VendorsModel [vendorCode=" + vendorCode + ", shipmentID=" + shipmentID + ", caseModel=" + caseModel
				+ "]";
	}
	
}
