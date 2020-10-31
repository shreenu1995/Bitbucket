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
public class TransitTerminalSetUpResponse extends AFCSCommonResponse {

  @NotEmpty(message = "PtoName is required")
  @Size(max = 64)
  private String ptoName;

  @NotEmpty(message = "PtoId is required")
  @Size(max = 64)
  private String ptoId;

  @NotEmpty(message = "DeviceId is required")
  @Size(max = 20)
  private String deviceId;

  @NotEmpty(message = "ShiftBatchNumber is required")
  @Size(max = 3)
  private String shiftBatchNumber;

}
