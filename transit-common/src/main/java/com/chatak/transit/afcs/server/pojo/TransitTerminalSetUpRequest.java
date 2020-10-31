/**
 * @author Girmiti Software
 */
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
public class TransitTerminalSetUpRequest extends AFCSCommonRequest {

  private static final long serialVersionUID = 6617763102672787627L;

  @NotEmpty(message = "DeviceSerialNumber is required")
  @Size(max = 12)
  private String deviceSerialNumber;

  @NotEmpty(message = "DeviceModelNumber is required")
  @Size(max = 20)
  private String deviceModelNumber;

}
