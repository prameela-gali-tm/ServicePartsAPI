package com.toyota.scs.serviceparts.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="SP_PART_TRANS")
public class PartTransEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="partTransSizeSeqGen")
	@SequenceGenerator(name = "partTransSizeSeqGen", sequenceName = "SPADM.sp_part_trans_seq", initialValue = 1, allocationSize = 10)
	@Column(name="PART_TRANS_ID")
	private long id;
	
	@Column(name="SUPPLIER_TOTAL")
	private long supplierTotal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRANSMISSION_DATE")
	private Date transmussionDate;
	
	@Column(name="SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="FULLFILLED_QUANTITY")
	private long fullfilledQuantity;

	@Column(name = "PART_ID")
	private long partId;
	
	@Column(name = "CASE_ID")
	private long caseId;
	
	@Column(name = "ORDER_ID")
	private long orderId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Transient
	private String caseNumber;
	
	@Transient
	private String partNumber;
	
	@Transient
	private String poLineItemNumber;
	
	@Transient
	private String poNumber;
	
	@Transient
	private String deliveryDueDate;
	
	@Transient
	private List<String> serialNumberList;
	
	
	
	public PartTransEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartTransEntity(long id, long supplierTotal, Date transmussionDate, String serialNumber, String status,
			long fullfilledQuantity, long partId, long caseId, long orderId, Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.supplierTotal = supplierTotal;
		this.transmussionDate = transmussionDate;
		this.serialNumber = serialNumber;
		this.status = status;
		this.fullfilledQuantity = fullfilledQuantity;
		this.partId = partId;
		this.caseId = caseId;
		this.orderId = orderId;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	

	public long getSupplierTotal() {
		return supplierTotal;
	}

	public void setSupplierTotal(long supplierTotal) {
		this.supplierTotal = supplierTotal;
	}

	public Date getTransmussionDate() {
		return transmussionDate;
	}

	public void setTransmussionDate(Date transmussionDate) {
		this.transmussionDate = transmussionDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getFullfilledQuantity() {
		return fullfilledQuantity;
	}

	public void setFullfilledQuantity(long fullfilledQuantity) {
		this.fullfilledQuantity = fullfilledQuantity;
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPoLineItemNumber() {
		return poLineItemNumber;
	}

	public void setPoLineItemNumber(String poLineItemNumber) {
		this.poLineItemNumber = poLineItemNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(String deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getSerialNumberList() {
		return serialNumberList;
	}

	public void setSerialNumberList(List<String> serialNumberList) {
		this.serialNumberList = serialNumberList;
	}
	
	
}
