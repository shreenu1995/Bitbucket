package com.chatak.transit.afcs.server.pojo;

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
public class TransitLocationUpdateRequest extends AFCSCommonRequest {

  private static final long serialVersionUID = -4195383106324752107L;

  @NotEmpty(message = "Latitude is required")
  @Size(max = 9)
  private String latitude;
  
  @NotEmpty(message = "Longitude is required")
  @Size(max = 10)
  private String longitude;
  
  @NotEmpty(message = "DeviceId is required")
  @Size(max = 32)
  private String deviceId;
}
