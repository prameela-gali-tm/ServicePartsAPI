package com.toyota.scs.serviceparts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SP_EXCEPTION")
public class ExceptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="EXCEPTION_ID")
	private long exceptionId;
	
	@Column(name="EXCEPTION_CODE")
	private long exceptionCode;
	
	@Column(name="COMMENTS")
	private String comments;

	@Column(name="CASE_ID")
	private long caseId;
	
	public ExceptionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public long getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(long exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}



	public long getCaseId() {
		return caseId;
	}



	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}



	public ExceptionEntity(long exceptionId, long exceptionCode, String comments, long caseId) {
		super();
		this.exceptionId = exceptionId;
		this.exceptionCode = exceptionCode;
		this.comments = comments;
		this.caseId = caseId;
	}
	
	
	
}
