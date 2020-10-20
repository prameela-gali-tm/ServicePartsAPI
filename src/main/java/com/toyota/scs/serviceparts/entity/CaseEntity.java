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
@Table(name="SP_CASE")
public class CaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="CASE_ID")
	private long id;
	
	@Column(name="CASE_NUMBER")
	private String caseNumber;
	
	@Column(name="CONFIRMATION_NUMBER")
	private String confirmationNumber;
	 
	@Column(name="STATUS")
	private String status;
	
	/*
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "SHIPMENT_ID", nullable = false)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 * 
	 * @JsonIgnore private Shipment Shipment;
	 */
	@Column(name="SHIPMENT_ID")
	 private Long shipment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public CaseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseEntity(long id, String caseNumber, String confirmationNumber, String status, Long shipment,
			Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.caseNumber = caseNumber;
		this.confirmationNumber = confirmationNumber;
		this.status = status;
		this.shipment = shipment;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getShipment() {
		return shipment;
	}

	public void setShipment(Long shipment) {
		this.shipment = shipment;
	}

	
	
}
