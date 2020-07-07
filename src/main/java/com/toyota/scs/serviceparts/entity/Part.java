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
@Table(name="SP_PART")
public class Part implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="PART_ID")
	private long partId;
	
	@Column(name="PART_NUMBER")
	private String partNumber;
	
	@Column(name="LINE_ITEM_NUMBER")
	private String lineItemNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DDD")
	private Date DDD;
	
	@Column(name="PART_DESC")
	private String partDesc;
	
	@Column(name="ORDER_QUANTITY")
	private long orderQuantity;
	
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

	public Part() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Part(long partId, String partNumber, String lineItemNumber, Date dDD, String partDesc, long orderQuantity,
			String vendorPartNumber, String directShip, String homePosition, Date transmissionDate,
			String orderRefNumber, String dealer, String status, String modifiedBy, Date modifiedDate, long orderId) {
		super();
		this.partId = partId;
		this.partNumber = partNumber;
		this.lineItemNumber = lineItemNumber;
		this.DDD = dDD;
		this.partDesc = partDesc;
		this.orderQuantity = orderQuantity;
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
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
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

	public Date getDDD() {
		return DDD;
	}

	public void setDDD(Date dDD) {
		DDD = dDD;
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
	
	
}
