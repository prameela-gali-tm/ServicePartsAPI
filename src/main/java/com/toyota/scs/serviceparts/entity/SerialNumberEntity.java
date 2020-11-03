package com.toyota.scs.serviceparts.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SP_SERIAL_NUMBER")
public class SerialNumberEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="SERIAL_ID")
	private long id;
	
	@Column(name="PART_TRANS_ID")
	private long partTransId;
	
	@Column(name="SERIAL_NUMBER")
	private String serialNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public long getPartTransId() {
		return partTransId;
	}

	public void setPartTransId(long partTransId) {
		this.partTransId = partTransId;
	}
	
	
	public SerialNumberEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SerialNumberEntity(long id, long partTransId, String serialNumber, Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.partTransId = partTransId;
		this.serialNumber = serialNumber;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}


	
	
}
