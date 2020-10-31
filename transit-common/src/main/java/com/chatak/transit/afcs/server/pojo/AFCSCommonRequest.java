package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AFCSCommonRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Size(max = 64)
  private String requestID;

  @Size(max = 64)
  private String timeZone;

  @Size(max = 10)
  private String originChannel;

  @Size(max = 32)
  private String ptoId;

  @NotEmpty(message = "CurrentDateTime is required")
  @Size(max = 19)
  private String currentDateTime;

  @NotEmpty(message = "CheckSum is required")
  @Size(max = 32)
  private String checkSum;

public String getRequestID() {
	return requestID;
}

public void setRequestID(String requestID) {
	this.requestID = requestID;
}

public String getTimeZone() {
	return timeZone;
}

public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
}

public String getOriginChannel() {
	return originChannel;
}

public void setOriginChannel(String originChannel) {
	this.originChannel = originChannel;
}

public String getPtoId() {
	return ptoId;
}

public void setPtoId(String ptoId) {
	this.ptoId = ptoId;
}

public String getCurrentDateTime() {
	return currentDateTime;
}

public void setCurrentDateTime(String currentDateTime) {
	this.currentDateTime = currentDateTime;
}

public String getCheckSum() {
	return checkSum;
}

public void setCheckSum(String checkSum) {
	this.checkSum = checkSum;
}
  
  
}
