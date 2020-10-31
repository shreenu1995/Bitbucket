package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinancialTxnDataRequest implements Serializable {

	private static final long serialVersionUID = 1999909198574920563L;

	private String userId;
	private String employeeId;
	private String ptoOperationId;
	private String deviceId;
	private String financialTxnType;
	private String dateTime;
	private String reservedSession;
	private String noOfField;
	private List<FinancialDataObject> fields = new ArrayList<>();
	private String shiftCode;
	private String shiftBatchNo;
	private String tripNo;
	private String paymentHostBatchNo;
	private String paymentHostTerminalId;
	private String reservedFinance;
	private String softwareVersion;
	private String masterVersion;
	private String deviceSerialNo;
	private String deviceTypeName;
	private String deviceModelNo;
	private String deviceComponentsVersion;
	private String reservedVersion;
	private String checksum;

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getFinancialTxnType() {
		return financialTxnType;
	}

	public void setFinancialTxnType(String financialTxnType) {
		this.financialTxnType = financialTxnType;
	}

	public String getReservedSession() {
		return reservedSession;
	}

	public void setReservedSession(String reservedSession) {
		this.reservedSession = reservedSession;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getNoOfField() {
		return noOfField;
	}

	public void setNoOfField(String noOfField) {
		this.noOfField = noOfField;
	}

	public List<FinancialDataObject> getFields() {
		return fields;
	}

	public void setFields(List<FinancialDataObject> fields) {
		this.fields = fields;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getShiftBatchNo() {
		return shiftBatchNo;
	}

	public void setShiftBatchNo(String shiftBatchNo) {
		this.shiftBatchNo = shiftBatchNo;
	}

	public String getTripNo() {
		return tripNo;
	}

	public void setTripNo(String tripNo) {
		this.tripNo = tripNo;
	}

	public String getPaymentHostBatchNo() {
		return paymentHostBatchNo;
	}

	public void setPaymentHostBatchNo(String paymentHostBatchNo) {
		this.paymentHostBatchNo = paymentHostBatchNo;
	}

	public String getPaymentHostTerminalId() {
		return paymentHostTerminalId;
	}

	public void setPaymentHostTerminalId(String paymentHostTerminalId) {
		this.paymentHostTerminalId = paymentHostTerminalId;
	}

	public String getReservedFinance() {
		return reservedFinance;
	}

	public void setReservedFinance(String reservedFinance) {
		this.reservedFinance = reservedFinance;
	}

	public String getMasterVersion() {
		return masterVersion;
	}

	public void setMasterVersion(String masterVersion) {
		this.masterVersion = masterVersion;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public String getDeviceComponentsVersion() {
		return deviceComponentsVersion;
	}

	public void setDeviceComponentsVersion(String deviceComponentsVersion) {
		this.deviceComponentsVersion = deviceComponentsVersion;
	}

	public String getDeviceModelNo() {
		return deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getReservedVersion() {
		return reservedVersion;
	}

	public void setReservedVersion(String reservedVersion) {
		this.reservedVersion = reservedVersion;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
