package com.toyota.scs.serviceparts.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SP_CASE")
public class Case implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="CASE_ID")
	private long caseId;
	
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
	 private long Shipment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

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

	public long getShipment() {
		return Shipment;
	}

	public void setShipment(long shipment) {
		Shipment = shipment;
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

	public Case() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Case(long caseId, String caseNumber, String confirmationNumber, String status, long shipment,
			Date modifiedDate, String modifiedBy) {
		super();
		this.caseId = caseId;
		this.caseNumber = caseNumber;
		this.confirmationNumber = confirmationNumber;
		this.status = status;
		this.Shipment = shipment;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}
	
	
}
