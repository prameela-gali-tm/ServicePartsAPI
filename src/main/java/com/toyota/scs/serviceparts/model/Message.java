package com.toyota.scs.serviceparts.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Message
 */
@XmlRootElement(name="message")
public class Message   {
  @JsonProperty("keyObject")
  private String keyObject = null;
  @JsonProperty("message")
  private List<String> errorMessages = new ArrayList<String>();
  @JsonProperty("type")
  private String type = null;

  public String getType() {
	return type;
  }


	public void setType(String type) {
		this.type = type;
	}


public Message keyObject(String keyObject) {
    this.keyObject = keyObject;
    return this;
  }

 
  public String getKeyObject() {
    return keyObject;
  }

  public void setKeyObject(String keyObject) {
    this.keyObject = keyObject;
  }


	public List<String> getErrorMessages() {
		return errorMessages;
	}
	
	
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

  

}

