package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="modelApiResponse")
public class ModelApiResponse {

	@JsonProperty("code")
	private Integer code ;
	
	 @JsonProperty("confirmationNumber")
	  private String confirmationNumber ;
	 
	 @JsonProperty("messages")
	  private List<Message> messages = new ArrayList<Message>();
	 
	 @JsonProperty("confirmedPurchaseOrders")
	 private List<String> confirmedPurchaseOrders = new ArrayList<String>();
	 
	 @JsonProperty("confirmedTrailer")
	 private String confirmedTrailer;
	 
	 @JsonProperty("responseCaseBuildDetails")
	 private ResponseCaseBuildModel responseCaseBuildDetails;
	public ModelApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<String> getConfirmedPurchaseOrders() {
		return confirmedPurchaseOrders;
	}

	public void setConfirmedPurchaseOrders(List<String> confirmedPurchaseOrders) {
		this.confirmedPurchaseOrders = confirmedPurchaseOrders;
	}

	public String getConfirmedTrailer() {
		return confirmedTrailer;
	}

	public void setConfirmedTrailer(String confirmedTrailer) {
		this.confirmedTrailer = confirmedTrailer;
	}

	public ResponseCaseBuildModel getResponseCaseBuildDetails() {
		return responseCaseBuildDetails;
	}

	public void setResponseCaseBuildDetails(ResponseCaseBuildModel responseCaseBuildDetails) {
		this.responseCaseBuildDetails = responseCaseBuildDetails;
	}

	public ModelApiResponse(Integer code, String confirmationNumber, List<Message> messages,
			List<String> confirmedPurchaseOrders, String confirmedTrailer,
			ResponseCaseBuildModel responseCaseBuildDetails) {
		super();
		this.code = code;
		this.confirmationNumber = confirmationNumber;
		this.messages = messages;
		this.confirmedPurchaseOrders = confirmedPurchaseOrders;
		this.confirmedTrailer = confirmedTrailer;
		this.responseCaseBuildDetails = responseCaseBuildDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((confirmationNumber == null) ? 0 : confirmationNumber.hashCode());
		result = prime * result + ((confirmedPurchaseOrders == null) ? 0 : confirmedPurchaseOrders.hashCode());
		result = prime * result + ((confirmedTrailer == null) ? 0 : confirmedTrailer.hashCode());
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((responseCaseBuildDetails == null) ? 0 : responseCaseBuildDetails.hashCode());
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
		ModelApiResponse other = (ModelApiResponse) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (confirmationNumber == null) {
			if (other.confirmationNumber != null)
				return false;
		} else if (!confirmationNumber.equals(other.confirmationNumber))
			return false;
		if (confirmedPurchaseOrders == null) {
			if (other.confirmedPurchaseOrders != null)
				return false;
		} else if (!confirmedPurchaseOrders.equals(other.confirmedPurchaseOrders))
			return false;
		if (confirmedTrailer == null) {
			if (other.confirmedTrailer != null)
				return false;
		} else if (!confirmedTrailer.equals(other.confirmedTrailer))
			return false;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (responseCaseBuildDetails == null) {
			if (other.responseCaseBuildDetails != null)
				return false;
		} else if (!responseCaseBuildDetails.equals(other.responseCaseBuildDetails))
			return false;
		return true;
	}

	

	
	
}
