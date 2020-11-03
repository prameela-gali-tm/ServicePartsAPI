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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SP_ROUTE_PATH")
public class RoutePathEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="ROUTE_PATH_ID")
	private long id;
	
	@Column(name = "ROUTE_SEQ")
	private long routeSeq;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	public RoutePathEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutePathEntity(long id, long routeSeq, Date modifiedDate, String modifiedBy) {
		super();
		this.id = id;
		this.routeSeq = routeSeq;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}



	public long getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSeq(long routeSeq) {
		this.routeSeq = routeSeq;
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
