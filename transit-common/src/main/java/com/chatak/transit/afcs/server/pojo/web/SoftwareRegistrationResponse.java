package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;

public class SoftwareRegistrationResponse extends WebResponse implements Serializable {

  private static final long serialVersionUID = -2532564778994357238L;
  private long softwareId;

  public long getSoftwareId() {
    return softwareId;
  }

  public void setSoftwareId(long softwareId) {
    this.softwareId = softwareId;
  }

  @Override
  public String toString() {

    StringBuilder response = new StringBuilder();
    return response.append("SoftwareRegistrationResponse [softwareId=").append(softwareId)
        .append(", getStatusCode()=").append(getStatusCode()).append(", getStatusMessage()=")
        .append(getStatusMessage()).append("]").toString();

  }
}
