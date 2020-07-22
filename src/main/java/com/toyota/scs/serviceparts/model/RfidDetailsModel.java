package com.toyota.scs.serviceparts.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
@XmlRootElement(name = "rfidDetails")
public class RfidDetailsModel {

	@JsonProperty("rfid")
	private Integer rfid;
	
	@JsonProperty("type")
	private String type;

	public Integer getRfid() {
		return rfid;
	}

	public void setRfid(Integer rfid) {
		this.rfid = rfid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RfidDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RfidDetailsModel(Integer rfid, String type) {
		super();
		this.rfid = rfid;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rfid == null) ? 0 : rfid.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		RfidDetailsModel other = (RfidDetailsModel) obj;
		if (rfid == null) {
			if (other.rfid != null)
				return false;
		} else if (!rfid.equals(other.rfid))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
