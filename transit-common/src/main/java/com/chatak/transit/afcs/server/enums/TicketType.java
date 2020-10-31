package com.chatak.transit.afcs.server.enums;

public enum TicketType {

  NORMAL("Normal"), PASS("Pass"), LUGGAGE("Luggage"), PENALTY("Penalty");

  private String value;

  private TicketType(String value) {
    this.setValue(value);
  }

  public String getValue() {
    return value;
  }

  private void setValue(String value) {
    this.value = value;
  }

  public static TicketType getEnum(String value) {
    for (TicketType v : values()) {
      if (v.getValue().equalsIgnoreCase(value)) {
        return v;
      }
    }
    throw new IllegalArgumentException();
  }

}
