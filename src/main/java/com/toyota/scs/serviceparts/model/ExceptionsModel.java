package com.toyota.scs.serviceparts.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "exceptions")
public class ExceptionsModel {

	@JsonProperty("exceptionCode")
	private String exceptionCode;
	
	@JsonProperty("comments")
	private String comments;

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ExceptionsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionsModel(String exceptionCode, String comments) {
		super();
		this.exceptionCode = exceptionCode;
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((exceptionCode == null) ? 0 : exceptionCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionsModel other = (ExceptionsModel) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (exceptionCode == null) {
			if (other.exceptionCode != null)
				return false;
		} else if (!exceptionCode.equals(other.exceptionCode))
			return false;
		return true;
	}
	
	
}
