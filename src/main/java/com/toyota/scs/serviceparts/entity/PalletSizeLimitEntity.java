package com.toyota.scs.serviceparts.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SP_PALLET_SIZE_LIMIT")
public class PalletSizeLimitEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="palletSizeSeqGen")
	@SequenceGenerator(name = "palletSizeSeqGen", sequenceName = "SPADM.sp_pallet_size_seq", initialValue = 1, allocationSize = 10)
	@Column(name="ID")
	private long id;
	
	@Column(name="DESTINATION_FD")
	private String destinationFd;
	
	@Column(name="HOME_POSITION")
	private String homePosition;
	
	@Column(name="LENGTH")
	private String length;
	
	@Column(name = "WIDTH")
	private String width;
	
	@Column(name = "HEIGHT")
	private String height;
	
	@Column(name = "MODIFIED_DATE", nullable = false)
	private Date modifiedDate;
	
	
	@Column(name = "MODIFIED_BY",nullable = false)
	private String modifiedBy;
	
	
	public PalletSizeLimitEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PalletSizeLimitEntity(String destinationFd, String homePosition, String length, String width,
			String height, Date modifiedDate, String modifiedBy) {
		super();
		this.destinationFd = destinationFd;
		this.homePosition = homePosition;
		this.length = length;
		this.width = width;
		this.height = height;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDestinationFd() {
		return destinationFd;
	}


	public void setDestinationFd(String destinationFd) {
		this.destinationFd = destinationFd;
	}


	public String getHomePosition() {
		return homePosition;
	}


	public void setHomePosition(String homePosition) {
		this.homePosition = homePosition;
	}


	public String getLength() {
		return length;
	}


	public void setLength(String length) {
		this.length = length;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String getHeight() {
		return height;
	}


	public void setHeight(String height) {
		this.height = height;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
