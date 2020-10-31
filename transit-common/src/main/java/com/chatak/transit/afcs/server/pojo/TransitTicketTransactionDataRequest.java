package com.chatak.transit.afcs.server.pojo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class TransitTicketTransactionDataRequest extends AFCSCommonRequest {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "DeviceId is required")
	@Size(max = 12)
	private String deviceId;

	@NotEmpty(message = "OperatorId is required")
	@Size(max = 12)
	private String operatorId;

	@NotNull
	private TicketDetail ticketDetails;

	private CardInfo cardInfo;

	private TripDetails tripDetails;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public TicketDetail getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(TicketDetail ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public TripDetails getTripDetails() {
		return tripDetails;
	}

	public void setTripDetails(TripDetails tripDetails) {
		this.tripDetails = tripDetails;
	}

}
