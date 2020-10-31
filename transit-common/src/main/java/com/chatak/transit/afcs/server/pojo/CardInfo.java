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
public class CardInfo implements Serializable {

  @NotEmpty(message = "CardNumber is required")
  @Size(max = 19)
  private String cardNumber;
  
  @NotEmpty(message = "Expiry is required")
  @Size(max = 4)
  private String expiry;
}
