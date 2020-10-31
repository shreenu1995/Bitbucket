package com.chatak.transit.afcs.server.enums;

public enum Status {
  ACTIVE("Active"),
  SUSPENDED("Suspended"),
  TERMINATED("Terminated");

  private String value;
  
  private Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
}
