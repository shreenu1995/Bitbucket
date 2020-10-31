package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDetail implements Serializable {

  @NotEmpty(message = "TicketNumber is required")
  @Size(max = 16)
  private String ticketNumber;
  
  @NotEmpty(message = "Fare is required")
  @Size(max = 8)
  private String fare;
  
  @NotEmpty(message = "PaymentMode is required")
  @Size(max = 5)
  private String paymentMode;
  
  @NotEmpty(message = "PassengerCount is required")
  @Size(max = 2)
  private String passengerCount;
  
  @NotNull
  private TicketType ticketType;
  
}
