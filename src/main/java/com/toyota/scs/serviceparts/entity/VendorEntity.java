package com.toyota.scs.serviceparts.entity;

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

@Table(name = "SP_VENDOR")
public class VendorEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "VENDOR_CODE", nullable = false,unique = true)
	private String vendorCode;
	@Column(name = "VENDOR_DESC", nullable = false)
	private String vendorDesc;
	@Column(name = "TRADING_PARTNER_ID", nullable = false)
	private String tradingPartnerId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE", nullable = false)
	private Date modifiedDate;
	@Column(name = "MODIFIED_BY",nullable = false)
	private String modifiedBy;
	
	
	  public VendorEntity() { super();} // TODO Auto-generated constructor stub }
	  
	  public VendorEntity(String vendorCode, String vendorDesc, String
	  tradingPartnerId, Date modifiedDate, String modifiedBy) { super();
	  this.vendorCode = vendorCode; this.vendorDesc = vendorDesc;
	  this.tradingPartnerId = tradingPartnerId; this.modifiedDate = modifiedDate;
	  this.modifiedBy = modifiedBy; }
	  
	  public long getId() { return id; }
	  
	  public void setId(long id) { this.id = id; }
	  
	  public String getVendorCode() { return vendorCode; }
	  
	  public void setVendorCode(String vendorCode) { this.vendorCode = vendorCode;
	  }
	  
	  public String getVendorDesc() { return vendorDesc; }
	  
	  public void setVendorDesc(String vendorDesc) { this.vendorDesc = vendorDesc;
	  }
	  
	  public String getTradingPartnerId() { return tradingPartnerId; }
	  
	  public void setTradingPartnerId(String tradingPartnerId) {
	  this.tradingPartnerId = tradingPartnerId; }
	  
	  public Date getModifiedDate() { return modifiedDate; }
	  
	  public void setModifiedDate(Date modifiedDate) { this.modifiedDate =
	  modifiedDate; }
	  
	  public String getModifiedBy() { return modifiedBy; }
	  
	  public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy;
	  }
	 
}
