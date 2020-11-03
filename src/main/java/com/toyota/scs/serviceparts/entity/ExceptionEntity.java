package com.toyota.scs.serviceparts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SP_EXCEPTION")
public class ExceptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="excepSeqGen")
	@SequenceGenerator(name = "excepSeqGen", sequenceName = "SPADM.sp_exception_seq", initialValue = 1, allocationSize = 10)
	@Column(name="EXCEPTION_ID")
	private long id;
	
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



	public ExceptionEntity(long id, long exceptionCode, String comments, long caseId) {
		super();
		this.id = id;
		this.exceptionCode = exceptionCode;
		this.comments = comments;
		this.caseId = caseId;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
