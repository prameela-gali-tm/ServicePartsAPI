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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SP_ORDER")
public class OrderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="orderSeqGen")
	@SequenceGenerator(name = "orderSeqGen", sequenceName = "SPADM.SP_ORDER_SEQ", initialValue = 1, allocationSize = 10)
	@Column(name="ORDER_ID")
	private long id;
	
	@Column(name="PO_NUMBER")
	private String poNumber;
	
	@Column(name="ORDER_TYPE")
	private String orderType;
	
	@Column(name="FINAL_DESTINATION")
	private String finalDestination;
	@Column(name="DEALER_CODE")
	private String dealerCode;
	@Column(name="trans_code")
	private String transCode;
	@Column(name="dealer_order")
	private String dealerOrder;
	
	@Column(name="DIRECT_SHIP_FLAG")
	private Boolean directShipFlag;

	@Column(name = "order_priority")
	private String orderPriority;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;
	
	/*
	 * @OneToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "VENDOR_CODE", nullable = false)
	 * 
	 * @JsonIgnore private Vendor vendor;
	 */
	@Column(name="VENDOR_CODE")
	private String vendorCode;

	@Column(name = "FINAL_DESTINATION_DESC")
	private String finalDestDesc;
	
	public OrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	




	public OrderEntity(long id, String poNumber, String orderType, String finalDestination, String dealerCode,
			String transCode, String dealerOrder, Boolean directShipFlag, String modifiedBy, Date modifiedDate,
			String vendorCode,String orderPriority) {
		super();
		this.id = id;
		this.poNumber = poNumber;
		this.orderType = orderType;
		this.finalDestination = finalDestination;
		this.dealerCode = dealerCode;
		this.transCode = transCode;
		this.dealerOrder = dealerOrder;
		this.directShipFlag = directShipFlag;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.vendorCode = vendorCode;
		this.orderPriority=orderPriority;
	}









	public String getDealerOrder() {
		return dealerOrder;
	}









	public void setDealerOrder(String dealerOrder) {
		this.dealerOrder = dealerOrder;
	}









	public String getFinalDestination() {
		return finalDestination;
	}




	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}




	public String getDealerCode() {
		return dealerCode;
	}

	public String getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}




	public Boolean getDirectShipFlag() {
		return directShipFlag;
	}




	public void setDirectShipFlag(Boolean directShipFlag) {
		this.directShipFlag = directShipFlag;
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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}




	public String getTransCode() {
		return transCode;
	}




	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}









	public String getFinalDestDesc() {
		return finalDestDesc;
	}









	public void setFinalDestDesc(String finalDestDesc) {
		this.finalDestDesc = finalDestDesc;
	}


	
	
}
