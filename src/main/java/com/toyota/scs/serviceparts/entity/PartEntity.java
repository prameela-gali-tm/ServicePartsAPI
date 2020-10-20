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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="SP_PART")
public class PartEntity implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="PART_ID")
	private long id;
	
	@Column(name="PART_NUMBER")
	private String partNumber;
	
	@Column(name="LINE_ITEM_NUMBER")
	private String lineItemNumber;
	
	
	@Column(name="DDD")
	private Date deliveryDueDate;
	
	@Column(name="PART_DESC")
	private String partDesc;
	
	@Column(name="ORDER_QUANTITY")
	private long orderQuantity;
	
	@Column(name="OUTSTANDING_QUANTITY")
	private long outstandingQuantity;
	
	@Column(name="VENDOR_PART_NUMBER")
	private String vendorPartNumber;
	
	@Column(name="DIRECT_SHIP")
	private String directShip;
	
	@Column(name="HOME_POSITION")
	private String homePosition;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRANSMISSION_DATE")
	private Date transmissionDate;
	
	@Column(name="ORDER_REF_NUMBER")
	private String orderRefNumber;
	
	@Column(name="DEALER")
	private String dealer;
	 
	@Column(name="STATUS") 
	private String status;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;
	
	//@ManyToOne(fetch = FetchType.LAZY, optional = false)
   // @JoinColumn(name = "ORDER_ID", nullable = false)
   // @OnDelete(action = OnDeleteAction.CASCADE)
   // @JsonIgnore
   // private Order order;
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="CONTAINER_ID")
	private String containerID;
	
	@Column(name="SERIAL_NUMBER")
	private String serialNumber;
	
	@Column(name="SUB_PART_NUMBER")
	private long subPartNumber;
		  
		  

	@Transient
	private String poNumber;
	
	@Transient
	private String orderType;
	
	@Transient
	private String vendorCode;	
	
	
	public PartEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getLineItemNumber() {
		return lineItemNumber;
	}

	public void setLineItemNumber(String lineItemNumber) {
		this.lineItemNumber = lineItemNumber;
	}

	

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}

	public String getDirectShip() {
		return directShip;
	}

	public void setDirectShip(String directShip) {
		this.directShip = directShip;
	}

	public String getHomePosition() {
		return homePosition;
	}

	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}

	public Date getTransmissionDate() {
		return transmissionDate;
	}

	public void setTransmissionDate(Date transmissionDate) {
		this.transmissionDate = transmissionDate;
	}

	public String getOrderRefNumber() {
		return orderRefNumber;
	}

	public void setOrderRefNumber(String orderRefNumber) {
		this.orderRefNumber = orderRefNumber;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public Date getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(Date deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}


	public long getOutstandingQuantity() {
		return outstandingQuantity;
	}


	public void setOutstandingQuantity(long outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}


	public String getContainerID() {
		return containerID;
	}


	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public long getSubPartNumber() {
		return subPartNumber;
	}


	public void setSubPartNumber(long subPartNumber) {
		this.subPartNumber = subPartNumber;
	}


	public PartEntity(long id, String partNumber, String lineItemNumber, Date deliveryDueDate, String partDesc,
			long orderQuantity, long outstandingQuantity, String vendorPartNumber, String directShip,
			String homePosition, Date transmissionDate, String orderRefNumber, String dealer, String status,
			String modifiedBy, Date modifiedDate, long orderId, String containerID, String serialNumber,
			long subPartNumber) {
		super();
		this.id = id;
		this.partNumber = partNumber;
		this.lineItemNumber = lineItemNumber;
		this.deliveryDueDate = deliveryDueDate;
		this.partDesc = partDesc;
		this.orderQuantity = orderQuantity;
		this.outstandingQuantity = outstandingQuantity;
		this.vendorPartNumber = vendorPartNumber;
		this.directShip = directShip;
		this.homePosition = homePosition;
		this.transmissionDate = transmissionDate;
		this.orderRefNumber = orderRefNumber;
		this.dealer = dealer;
		this.status = status;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.orderId = orderId;
		this.containerID = containerID;
		this.serialNumber = serialNumber;
		this.subPartNumber = subPartNumber;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}
	
	
}
