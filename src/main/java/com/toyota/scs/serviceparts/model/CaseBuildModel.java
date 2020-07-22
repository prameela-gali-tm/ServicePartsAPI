package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseBuildModel {

	@JsonProperty("vendorCode")
	private String vendorCode;
	
	@JsonProperty("exceptions")
	private List<ExceptionsModel> exceptions = new ArrayList<ExceptionsModel>();
	
	@JsonProperty("cases")
	private List<CaseModel> cases = new ArrayList<CaseModel>();	


	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public List<ExceptionsModel> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<ExceptionsModel> exceptions) {
		this.exceptions = exceptions;
	}

	public List<CaseModel> getCases() {
		return cases;
	}

	public void setCases(List<CaseModel> cases) {
		this.cases = cases;
	}

	public CaseBuildModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseBuildModel(String vendorCode, List<ExceptionsModel> exceptions, List<CaseModel> cases) {
		super();
		this.vendorCode = vendorCode;
		this.exceptions = exceptions;
		this.cases = cases;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cases == null) ? 0 : cases.hashCode());
		result = prime * result + ((exceptions == null) ? 0 : exceptions.hashCode());
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
		CaseBuildModel other = (CaseBuildModel) obj;
		if (cases == null) {
			if (other.cases != null)
				return false;
		} else if (!cases.equals(other.cases))
			return false;
		if (exceptions == null) {
			if (other.exceptions != null)
				return false;
		} else if (!exceptions.equals(other.exceptions))
			return false;
		if (vendorCode == null) {
			if (other.vendorCode != null)
				return false;
		} else if (!vendorCode.equals(other.vendorCode))
			return false;
		return true;
	}
	
	
}
