package com.toyota.scs.serviceparts.model;

public class AsnTPNAModel {
	public String poNumber;
    public int shipmentID;
    public String scacCode;
    public String containerNumber;
    public int bolQty;
    public int shipmentWeight;
    public String shipmentDate;
    public String vendorCode;
    public String transmissionDate;
    public String driverFirstName;
    public String driverLastName;
    public String partNumber;
    public int vendorShipQty;
    public String caseID;
    public String ddd;
    public String hp;
    public String lineNumber;
    public String trackingFlag;
    public int totalCount;
    public int rowNumber;
    
    public AsnTPNAModel() {
    	super();
    }
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public int getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(int shipmentID) {
		this.shipmentID = shipmentID;
	}
	public String getScacCode() {
		return scacCode;
	}
	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public int getBolQty() {
		return bolQty;
	}
	public void setBolQty(int bolQty) {
		this.bolQty = bolQty;
	}
	public int getShipmentWeight() {
		return shipmentWeight;
	}
	public void setShipmentWeight(int shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getTransmissionDate() {
		return transmissionDate;
	}
	public void setTransmissionDate(String transmissionDate) {
		this.transmissionDate = transmissionDate;
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
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public int getVendorShipQty() {
		return vendorShipQty;
	}
	public void setVendorShipQty(int vendorShipQty) {
		this.vendorShipQty = vendorShipQty;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getTrackingFlag() {
		return trackingFlag;
	}
	public void setTrackingFlag(String trackingFlag) {
		this.trackingFlag = trackingFlag;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bolQty;
		result = prime * result + ((caseID == null) ? 0 : caseID.hashCode());
		result = prime * result + ((containerNumber == null) ? 0 : containerNumber.hashCode());
		result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
		result = prime * result + ((driverFirstName == null) ? 0 : driverFirstName.hashCode());
		result = prime * result + ((driverLastName == null) ? 0 : driverLastName.hashCode());
		result = prime * result + ((hp == null) ? 0 : hp.hashCode());
		result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + rowNumber;
		result = prime * result + ((scacCode == null) ? 0 : scacCode.hashCode());
		result = prime * result + ((shipmentDate == null) ? 0 : shipmentDate.hashCode());
		result = prime * result + shipmentID;
		result = prime * result + shipmentWeight;
		result = prime * result + totalCount;
		result = prime * result + ((trackingFlag == null) ? 0 : trackingFlag.hashCode());
		result = prime * result + ((transmissionDate == null) ? 0 : transmissionDate.hashCode());
		result = prime * result + ((vendorCode == null) ? 0 : vendorCode.hashCode());
		result = prime * result + vendorShipQty;
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
		AsnTPNAModel other = (AsnTPNAModel) obj;
		if (bolQty != other.bolQty)
			return false;
		if (caseID == null) {
			if (other.caseID != null)
				return false;
		} else if (!caseID.equals(other.caseID))
			return false;
		if (containerNumber == null) {
			if (other.containerNumber != null)
				return false;
		} else if (!containerNumber.equals(other.containerNumber))
			return false;
		if (ddd == null) {
			if (other.ddd != null)
				return false;
		} else if (!ddd.equals(other.ddd))
			return false;
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
		if (hp == null) {
			if (other.hp != null)
				return false;
		} else if (!hp.equals(other.hp))
			return false;
		if (lineNumber == null) {
			if (other.lineNumber != null)
				return false;
		} else if (!lineNumber.equals(other.lineNumber))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		if (rowNumber != other.rowNumber)
			return false;
		if (scacCode == null) {
			if (other.scacCode != null)
				return false;
		} else if (!scacCode.equals(other.scacCode))
			return false;
		if (shipmentDate == null) {
			if (other.shipmentDate != null)
				return false;
		} else if (!shipmentDate.equals(other.shipmentDate))
			return false;
		if (shipmentID != other.shipmentID)
			return false;
		if (shipmentWeight != other.shipmentWeight)
			return false;
		if (totalCount != other.totalCount)
			return false;
		if (trackingFlag == null) {
			if (other.trackingFlag != null)
				return false;
		} else if (!trackingFlag.equals(other.trackingFlag))
			return false;
		if (transmissionDate == null) {
			if (other.transmissionDate != null)
				return false;
		} else if (!transmissionDate.equals(other.transmissionDate))
			return false;
		if (vendorCode == null) {
			if (other.vendorCode != null)
				return false;
		} else if (!vendorCode.equals(other.vendorCode))
			return false;
		if (vendorShipQty != other.vendorShipQty)
			return false;
		return true;
	}

	public AsnTPNAModel(String poNumber, int shipmentID, String scacCode, String containerNumber, int bolQty,
			int shipmentWeight, String shipmentDate, String vendorCode, String transmissionDate, String driverFirstName,
			String driverLastName, String partNumber, int vendorShipQty, String caseID, String ddd, String hp,
			String lineNumber, String trackingFlag, int totalCount, int rowNumber) {
		super();
		this.poNumber = poNumber;
		this.shipmentID = shipmentID;
		this.scacCode = scacCode;
		this.containerNumber = containerNumber;
		this.bolQty = bolQty;
		this.shipmentWeight = shipmentWeight;
		this.shipmentDate = shipmentDate;
		this.vendorCode = vendorCode;
		this.transmissionDate = transmissionDate;
		this.driverFirstName = driverFirstName;
		this.driverLastName = driverLastName;
		this.partNumber = partNumber;
		this.vendorShipQty = vendorShipQty;
		this.caseID = caseID;
		this.ddd = ddd;
		this.hp = hp;
		this.lineNumber = lineNumber;
		this.trackingFlag = trackingFlag;
		this.totalCount = totalCount;
		this.rowNumber = rowNumber;
	}

	
    
    
    
    
    
}
