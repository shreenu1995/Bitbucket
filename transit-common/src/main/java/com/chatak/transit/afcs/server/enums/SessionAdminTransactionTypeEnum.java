package com.chatak.transit.afcs.server.enums;

public enum SessionAdminTransactionTypeEnum {
	
	LOGIN("A001"), LOGOUT("A002"), SESSION_TIMEOUT("A003"), SESSION_ALIVE("A004"), SHIFTEND("F001") , TRIP_END("F002");

	private String type;

	SessionAdminTransactionTypeEnum(String type) {
		this.type = type;
	}

	public String getTypeValue() {
		return this.type;
	}

	public static SessionAdminTransactionTypeEnum getUserOperationType(String type) {
		SessionAdminTransactionTypeEnum[] allUserAttendanceTypes = SessionAdminTransactionTypeEnum.values();
		for (int i = 0, length = allUserAttendanceTypes.length; i < length; i++) {
			if (allUserAttendanceTypes[i].getTypeValue().equals(type))
				return allUserAttendanceTypes[i];
		}
		return null;
	}

}
