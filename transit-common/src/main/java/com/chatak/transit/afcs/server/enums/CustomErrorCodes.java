/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.enums;

public enum CustomErrorCodes {
	SUCCESS("0", "Success"),
	FAILURE("1", "Failure"),
	INVALID_PACKET_LENGTH("2", "Invalid Packet Length"),
	ORGANIZATION_NOT_EXIST("3", "Organization doesnot exist"),
	PTO_NOT_ACTIVE("4", "PTO is not active"),
	EQUIPMNET_NOT_EXIST("5", "Device does not exist"),
	DEVICE_NOT_ACTIVE("6", "DeviceId does not exist "),
	EMPLOYEE_NOT_EXIST("7", "Employee does not exist"),
	EMPLOYEE_NOT_ACTIVE("8", "Employee not active"),
	DEPOT_NOT_EXIST("9", "Depot does not exist"),
	DEPOT_NOT_ACTIVE("10", "Depot is not active"),
	INVALID_PAYMENT_MODE("11", "Invalid payment mode"),
	CARD_NOT_EXIST("12", "Invalid card number"),
	CARD_NOT_ACTIVE("13", "Inactive Card"),
	BLACKLISTED_CARD("14", "Blacklisted card"), 
	INVALID_SHIFT_ID("15", "Invalid Shift ID"),
	INVALID_TRANSACTION_ID("16", "Invalid Transaction ID"),
	INVALID_TICKET("17", "Duplicate Ticket Number"),
	INVALID_USERNAME("18", "User Name already Exist"),
	INVALID_ADMIN("19", "Admin does not Exist"),
	USER_NOT_EXIST("20", "User ID does not exist"),
	DEVICE_MODEL_NOT_EXIST("21", "Model number does not exist"),
	DEVICEOEMID_NOT_EXIST("22", "DeviceOemId does not exist"),
	WRONG_PASSWORD("23","Current Password does not exist"),
	PTO_OPERATION_NOT_EXIST("24", "PTO Operation does not exist"),
	PTO_OPERATION_NOT_ACTIVE("25", "PTO Operation is not active"),	
	SOFTWARE_ID_NOT_EXIST("26","Software id not exists"),
	SOFTWARE_NOT_EXIST("27","Software not exists"),
	SOFTWARE_DIRECTORY_NOT_EXIST("28","Software directory not exists"),
	SOFTWARE_VERSION_ALREADY_EXIST("29","Software version already exist "),
	ROUTE_NAME_ALREADY_EXIST("30", "Route name already exist"),
	DEVICE_NAME_ALREADY_EXIST("31", "Device name already exist"),
	INVALID_AMOUNT_COUNT_TXN_ID_TT01("32","Invalid amount or count for txnid TTO1"),
	INVALID_AMOUNT_COUNT_TXN_ID_TT02("33","Invalid amount or count for txnid TT02"),
	INVALID_AMOUNT_COUNT_TXN_ID_TL01("34","Invalid amount or count for txnid TL01"),
	INVALID_AMOUNT_COUNT_TXN_ID_TP01("35","Invalid amount or count for txnid TP01"),
	INVALID_AMOUNT_COUNT_TXN_ID_CP01("36","Invalid amount or count for txnid CP01"),
	INVALID_AMOUNT_COUNT_TXN_ID_TK01("37","Invalid amount or count for txnid TK01"),
    INVALID_FINANCIAL_TXN_ID("39","Invalid Financial Transaction Type"),
    VERSION_UPDATE_REQUIRED("40","Version Update  required"),
	VERSION_UPDATE_NOT_REQUIRED("41","Version Update not required"),
	VERSION_WRONG("42","Version is not valid"),
	DEVICE_MODEL_NAME("43","Device Model Already Exist.."),
	DEVICE_TYPE_NAME("44","DeviceTypeName does not exist"),
	USER_NOT_ACTIVE("45", "User Not Active"),
	DEVICE_MODEL_NAME_NOT_EXIST("46","Device Model Name does not exist"),
	OPERATOR_ID_NOT_EXIST("47","Operator Id Does Not Exist"),
	SERVER_ERROR("50", "Internal Server Error"),
	RESET_PSWD_LINK_EXPIRED("51","Reset password link has expired"),
	INVALID_TIMESTAMP_FORMAT("53", "Timestamp format must be yyyy-mm-dd hh:mm:ss"),
    DEVICE_ID_PTO_OPERATION_ID("56","Device is not registered for this Pto OperationId"),
	USER_ID_PASSWORD("52","Password is not correct"),
	AFCS_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR("53","Old and new password should not be the same, please try with new password"),
	NXP_ADMIN_USER_CHANGEPASSWORD_OLDNEW_ERROR("54","Old and new password should not be the same, please try with new password"),
	SHIFT_BATCH_NO_ERROR("56","Shift Batch No is already exist"),
	STATION_ID_NOT_EXISTS("57","Station id not exists"),
	DEVICE_TYPE_ID_NOT_EXISTS("58","Device type id not exists"),
	TICKET_ORIGIN_STOP_IS_INVALID("59","ticketOriginStop is invalid"),
	TICKET_DESTINATION_STOP_IS_INVALID("60","ticketDestinationStop is invalid"),
	TRANSACTION_ID_EMPTY_AND_LENGTH("61","transactionId Can't be Null or length should be 4 digit"),
	USER_ID_EMPTY_AND_LENGTH("62","UserId Can't be Null or UserId length should be less than 35 char"),
	PTO_OPER_ID_EMPTY_AND_LENGTH("63","PtoOperationId Can't be Null or PtoOperationId length should be 12 char"),
	SOFTWARE_VERSION_EMPTY_AND_LENGTH("64","SoftwareVersion Can't be Null or SoftwareVersion length between 7-12 char"),
	DEVICE_TYPE_EMPTY_AND_LENGTH("65","DeviceType Can't be Null or DeviceType length between 1-35 char"),
	SOFTWARE_ID_EMPTY_AND_LENGTH("66","SoftwareId Can't be Null or length should be less than 6 digit"),
	PTO_ACTIVE("67", "PTO is active"),
	PREVIOUS_PSWD_INVALID("68","New password should not be same as your last 3 passwords"),
	DEPOT_ACTIVE("69", "Depot is active"),
	EMAIL_ALREADY_EXISTS("70","Email already exists"),
	INVALID_USER_PTO_OPERATION("71","Invalid User PTO Operation"),
	PTO_OPERATION_ACTIVE("72", "PTO OPeration is active"),
	DEVICE_MODEL_ID("73","deviceModelId Does Not Exist"),
	PRIVILEGE_ID_NOT_EXIST("74","Privilege Id does not exist"),
	ROLE_NAME_NOT_EXIST("75","Role Name does not exist"),
	INVALID_STATION_NAME("76", "StationName already exists"),
	INVALID_PINCODE("77", "PinCode should be a number"),
	INVALID_STOP_NAME("78", "Stop Name already exists"),
	DEVICE_MODEL("79","Device Model Does Not Exist.."),
	INVALID_DEPOT_NAME("80", "Depot Name already exists"),
	ROUTE_CODE_INVALID("81","RouteCode is invalid"),
	DEVICE_MANUFACTURER_ID_NOT_EXISTS("82","Device Manufacturer Id is invalid"),
	FARE_NAME_ALREADY_EXIST("83","Fare name already exist"),
	DEVICE_MANUFACTURER_ALREADY_EXIST("84","Device Manufacturer already exist"),
	VEHICLE_TYPE_ID_NOT_EXISTS("58","vehicle type id does not exists"),
	PRODUCTID_NOT_EXIST("85", "ProductId doesnot exist"),
	STAGE_ID_NOT_EXISTS("86", "Stage Id is invalid"),
	ROLE_EXIST("87","Role name already exist.Please choose different Role name"),
	INVALID_ORGANIZATION_NAME("88","Organization Name already exists"),
	INVALID_OPERTOR_NAME("89","Operator Name already exists"),
	INVALID_VEHICLE_TYPE("90","Vehicle Type Name already exists"),
	INVALID_VEHICLE_MANUFACTURER("91","Vehicle Manufacturer Name already exists"),
	INVALID_VEHICLE_MODEL("92","Vehicle Model Name already exists"),
	INVALID_PTO("93", "PTO Name already exists"),
	FIRST_TIME_LOGIN("94","First Time Login"),
	INVALID_DEVICE_MODEL("95", "Device Model already exists"),
	DEVICE_IMEI_NUMBER("96","Device IMEI Number Already Exist.."),
	ISSUER_EXIST("97", "Issuer name already exist, please choose different name"),
	PG_EXIST("98", "Payment Gateway name already exist, please choose different name"),
	USER_STATUS_TERMINATED("97", "User is blocked please contact admin"),
	USER_STATUS_SUSPENDED("98", "User is blocked please contact admin"),
	ROUTE_CODE_NOT_EXIST("99", "Route Code Does Not Exist"),
	PTO_NOT_EXIST("100", "Pto Id Does Not Exist"),
	USER_INACTIVE("101", "User is inactive"),
	STOP_CODE_NOT_EXIST("102", "Stop Code Does Not Exist"),
	BLACKLISTED_ACCOUNT_FILE("103","Blacklisted file Failed to Upload"),
	EMAIL_NOT_EXISTS("104", "Email Id does not exist");
	private String errorCode;
	private String errorMsg;
	
	CustomErrorCodes(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getCustomErrorCode() {
		return this.errorCode;
	}

	public String getCustomErrorMsg() {
		return this.errorMsg;
	}

	public static CustomErrorCodes getCustomErrorCode(String errorCode) {
		CustomErrorCodes[] allCustomErrorCodes = CustomErrorCodes.values();
		for (int i = 0, length = allCustomErrorCodes.length; i < length; i++) {
			if (allCustomErrorCodes[i].getCustomErrorCode().equalsIgnoreCase(errorCode))
				return allCustomErrorCodes[i];
		}
		return null;
	}

	public static CustomErrorCodes getCustomErrorMsg(String errorMsg) {
		CustomErrorCodes[] allCustomErrorCodes = CustomErrorCodes.values();
		for (int i = 0, length = allCustomErrorCodes.length; i < length; i++) {
			if (allCustomErrorCodes[i].getCustomErrorMsg().equalsIgnoreCase(errorMsg))
				return allCustomErrorCodes[i];
		}
		return null;
	}
}
