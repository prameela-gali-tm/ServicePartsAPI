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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SP_SHIPMENT")
public class ShipmentEntity implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shipmentSeqGen")
	@SequenceGenerator(name = "shipmentSeqGen", sequenceName = "SPADM.sp_shipment_seq", initialValue = 1, allocationSize = 10)
	@Column(name="SHIPMENT_ID")
	private long id;

	@Column(name = "ROUTE")
	private String route;

	@Column(name = "RUN")
	private long run;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SHIP_DATE")
	private Date shipDate;

	@Column(name = "TRAILER")
	private String trailer;

	@Column(name = "DRIVER_FIRST_NAME")
	private String driverFirstName;

	@Column(name = "DRIVER_LAST_NAME")
	private String driverLastName;

	@Column(name = "SCAC_CODE")
	private String scacCode;
	// added
	@Column(name = "VENDOR_CODE")
	private String vendorCode;

	@Column(name = "SUPPLIER_FIRST_NAME")
	private String supplierFirstName;

	@Column(name = "SUPPLIER_LAST_NAME")
	private String supplierLastName;

	@Column(name = "TRACKING_NUMBER")
	private String trackingNumber;

	@Column(name = "CONFIRMAATION_NUMBER")
	private String confirmationNumber;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "GEO_LONGITUDE")
	private String geoLongitude;

	@Column(name = "GEO_LATTITUDE")
	private String geoLattitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	public ShipmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentEntity(long id, String route, long run, Date shipDate, String trailer, String driverFirstName,
			String driverLastName, String scacCode, String vendorCode, String supplierFirstName,
			String supplierLastName, String trackingNumber, String confirmationNumber, String status,
			String geoLongitude, String geoLattitude, Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.route = route;
		this.run = run;
		this.shipDate = shipDate;
		this.trailer = trailer;
		this.driverFirstName = driverFirstName;
		this.driverLastName = driverLastName;
		this.scacCode = scacCode;
		this.vendorCode = vendorCode;
		this.supplierFirstName = supplierFirstName;
		this.supplierLastName = supplierLastName;
		this.trackingNumber = trackingNumber;
		this.confirmationNumber = confirmationNumber;
		this.status = status;
		this.geoLongitude = geoLongitude;
		this.geoLattitude = geoLattitude;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public long getRun() {
		return run;
	}

	public void setRun(long run) {
		this.run = run;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getScacCode() {
		return scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}

	public String getSupplierFirstName() {
		return supplierFirstName;
	}

	public void setSupplierFirstName(String supplierFirstName) {
		this.supplierFirstName = supplierFirstName;
	}

	public String getSupplierLastName() {
		return supplierLastName;
	}

	public void setSupplierLastName(String supplierLastName) {
		this.supplierLastName = supplierLastName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
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

	public String getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(String geoLongitude) {
		this.geoLongitude = geoLongitude;
	}

	public String getGeoLattitude() {
		return geoLattitude;
	}

	public void setGeoLattitude(String geoLattitude) {
		this.geoLattitude = geoLattitude;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

}
