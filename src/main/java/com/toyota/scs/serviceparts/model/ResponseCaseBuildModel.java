package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ResponseCaseBuildModel {

	@JsonProperty("vendorCode")
	private String vendorCode;
	
	@JsonProperty("cases")
	private List<ResponseCaseModel> cases = new ArrayList<ResponseCaseModel>();

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public List<ResponseCaseModel> getCases() {
		return cases;
	}

	public void setCases(List<ResponseCaseModel> cases) {
		this.cases = cases;
	}

	public ResponseCaseBuildModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseCaseBuildModel(String vendorCode, List<ResponseCaseModel> cases) {
		super();
		this.vendorCode = vendorCode;
		this.cases = cases;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cases == null) ? 0 : cases.hashCode());
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
		ResponseCaseBuildModel other = (ResponseCaseBuildModel) obj;
		if (cases == null) {
			if (other.cases != null)
				return false;
		} else if (!cases.equals(other.cases))
			return false;
		if (vendorCode == null) {
			if (other.vendorCode != null)
				return false;
		} else if (!vendorCode.equals(other.vendorCode))
			return false;
		return true;
	}
	
	
}
