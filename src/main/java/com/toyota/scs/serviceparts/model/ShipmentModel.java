package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "shipmentLoadDetails")
public class ShipmentModel {

	@JsonProperty("trailerNumber")
	private String trailerNumber;
	@JsonProperty("route")
	private String route;
	@JsonProperty("run")
	private String run;
	@JsonProperty("planPickupDate")
	private String planPickupDate;
	@JsonProperty("sealNumber")
	private String sealNumber;
	@JsonProperty("supplierFirstName")
	private String supplierFirstName;
	@JsonProperty("supplierLastName")
	private String supplierLastName;
	
	@JsonProperty("lpCode")
	private String lpCode;
	@JsonProperty("driverFirstName")
	private String driverFirstName;
	@JsonProperty("driverLastName")
	private String driverLastName;
	@JsonProperty("exceptions")
	private List<ExceptionsModel> exceptions = new ArrayList<ExceptionsModel>();
	@JsonProperty("vendors")
	private List<VendorsModel> vendors=new ArrayList<VendorsModel>();


	public ShipmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ShipmentModel(String trailerNumber, String route, String run, String planPickupDate, String sealNumber,
			String supplierFirstName, String supplierLastName, String lpCode, String driverFirstName,
			String driverLastName, List<ExceptionsModel> exceptions, List<VendorsModel> vendors) {
		super();
		this.trailerNumber = trailerNumber;
		this.route = route;
		this.run = run;
		this.planPickupDate = planPickupDate;
		this.sealNumber = sealNumber;
		this.supplierFirstName = supplierFirstName;
		this.supplierLastName = supplierLastName;
		this.lpCode = lpCode;
		this.driverFirstName = driverFirstName;
		this.driverLastName = driverLastName;
		this.exceptions = exceptions;
		this.vendors = vendors;
	}


	public String getTrailerNumber() {
		return trailerNumber;
	}


	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}


	public String getRoute() {
		return route;
	}


	public void setRoute(String route) {
		this.route = route;
	}


	public String getRun() {
		return run;
	}


	public void setRun(String run) {
		this.run = run;
	}


	public String getPlanPickupDate() {
		return planPickupDate;
	}


	public void setPlanPickupDate(String planPickupDate) {
		this.planPickupDate = planPickupDate;
	}


	public String getSealNumber() {
		return sealNumber;
	}


	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}


	public String getSupplierFirstName() {
		return supplierFirstName;
	}


	public void setSupplierFirstName(String supplierFirstName) {
		this.supplierFirstName = supplierFirstName;
	}


	public String getSupplierLastName() {
		return supplierLastName;
	}


	public void setSupplierLastName(String supplierLastName) {
		this.supplierLastName = supplierLastName;
	}


	public String getLpCode() {
		return lpCode;
	}


	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}


	public String getDriverFirstName() {
		return driverFirstName;
	}


	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}


	public String getDriverLastName() {
		return driverLastName;
	}


	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}


	public List<ExceptionsModel> getExceptions() {
		return exceptions;
	}


	public void setExceptions(List<ExceptionsModel> exceptions) {
		this.exceptions = exceptions;
	}


	public List<VendorsModel> getVendors() {
		return vendors;
	}


	public void setVendors(List<VendorsModel> vendors) {
		this.vendors = vendors;
	}


	@Override
	public String toString() {
		return "ShipmentModel [trailerNumber=" + trailerNumber + ", route=" + route + ", run=" + run
				+ ", planPickupDate=" + planPickupDate + ", sealNumber=" + sealNumber + ", supplierFirstName="
				+ supplierFirstName + ", supplierLastName=" + supplierLastName + ", lpCode=" + lpCode
				+ ", driverFirstName=" + driverFirstName + ", driverLastName=" + driverLastName + ", exceptions="
				+ exceptions + ", vendors=" + vendors + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverFirstName == null) ? 0 : driverFirstName.hashCode());
		result = prime * result + ((driverLastName == null) ? 0 : driverLastName.hashCode());
		result = prime * result + ((exceptions == null) ? 0 : exceptions.hashCode());
		result = prime * result + ((lpCode == null) ? 0 : lpCode.hashCode());
		result = prime * result + ((planPickupDate == null) ? 0 : planPickupDate.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + ((run == null) ? 0 : run.hashCode());
		result = prime * result + ((sealNumber == null) ? 0 : sealNumber.hashCode());
		result = prime * result + ((supplierFirstName == null) ? 0 : supplierFirstName.hashCode());
		result = prime * result + ((supplierLastName == null) ? 0 : supplierLastName.hashCode());
		result = prime * result + ((trailerNumber == null) ? 0 : trailerNumber.hashCode());
		result = prime * result + ((vendors == null) ? 0 : vendors.hashCode());
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
		ShipmentModel other = (ShipmentModel) obj;
		if (driverFirstName == null) {
			if (other.driverFirstName != null)
				return false;
		} else if (!driverFirstName.equals(other.driverFirstName))
			return false;
		if (driverLastName == null) {
			if (other.driverLastName != null)
				return false;
		} else if (!driverLastName.equals(other.driverLastName))
			return false;
		if (exceptions == null) {
			if (other.exceptions != null)
				return false;
		} else if (!exceptions.equals(other.exceptions))
			return false;
		if (lpCode == null) {
			if (other.lpCode != null)
				return false;
		} else if (!lpCode.equals(other.lpCode))
			return false;
		if (planPickupDate == null) {
			if (other.planPickupDate != null)
				return false;
		} else if (!planPickupDate.equals(other.planPickupDate))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (run == null) {
			if (other.run != null)
				return false;
		} else if (!run.equals(other.run))
			return false;
		if (sealNumber == null) {
			if (other.sealNumber != null)
				return false;
		} else if (!sealNumber.equals(other.sealNumber))
			return false;
		if (supplierFirstName == null) {
			if (other.supplierFirstName != null)
				return false;
		} else if (!supplierFirstName.equals(other.supplierFirstName))
			return false;
		if (supplierLastName == null) {
			if (other.supplierLastName != null)
				return false;
		} else if (!supplierLastName.equals(other.supplierLastName))
			return false;
		if (trailerNumber == null) {
			if (other.trailerNumber != null)
				return false;
		} else if (!trailerNumber.equals(other.trailerNumber))
			return false;
		if (vendors == null) {
			if (other.vendors != null)
				return false;
		} else if (!vendors.equals(other.vendors))
			return false;
		return true;
	}
	
	
	
}
