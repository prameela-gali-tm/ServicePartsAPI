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
@Table(name="SP_ROUTE_PATH_NODE")
public class RoutePathNodeEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ROUTE_PATH__NODE_ID")
	private long id;
	
	@Column(name="FACILITY")
	private long facility;
	 
	
	/*
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "ROUTE_PATH_ID", nullable = false)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 * 
	 * @JsonIgnore private RoutePath routePath;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "CASE_ID", nullable = false)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 * 
	 * @JsonIgnore private Case caseObje;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "SHIPMENT_ID", nullable = false)
	 * 
	 * @OnDelete(action = OnDeleteAction.CASCADE)
	 * 
	 * @JsonIgnore private Shipment shipment;
	 */
	@Column(name = "ROUTE_PATH_ID")
	private long routePathId;
	
	@Column(name = "CASE_ID")
	private long caseId;
	
	@Column(name = "SHIPMENT_ID")
	private long shipmentId;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	public RoutePathNodeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutePathNodeEntity(long id, long facility, long routePathId, long caseId, long shipmentId,
			Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.facility = facility;
		this.routePathId = routePathId;
		this.caseId = caseId;
		this.shipmentId = shipmentId;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	

	public long getFacility() {
		return facility;
	}

	public void setFacility(long facility) {
		this.facility = facility;
	}

	public long getRoutePathId() {
		return routePathId;
	}

	public void setRoutePathId(long routePathId) {
		this.routePathId = routePathId;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(long shipmentId) {
		this.shipmentId = shipmentId;
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
	
	
	
}
