/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;

import com.chatak.transit.afcs.server.constants.TerminalConstant;

public class TerminalSetUpRequest implements Serializable {

	private static final long serialVersionUID = 6617763102672787627L;

	private String deviceOemId;
	private String deviceModelNumber;
	private String reserverd;
	private String dateTime;

	public void parseSetupData(String data) {
		deviceOemId = data.substring(TerminalConstant.DEVICE_SERIAL_NUM_INITIAL_INDEX,
				TerminalConstant.DEVICE_SERIAL_NUM_FINAL_INDEX).trim();
		dateTime = data.substring(TerminalConstant.DATE_TIME_INITIAL_INDEX, TerminalConstant.DATE_TIME_FINAL_INDEX)
				.trim();
		deviceModelNumber = data.substring(TerminalConstant.DEVICE_MODEL_NUM_INITIAL_INDEX,
				TerminalConstant.DEVICE_MODEL_NUM_FINAL_INDEX).trim();
	}

	public String getDeviceOemId() {
		return deviceOemId;
	}

	public void setDeviceOemId(String deviceOemId) {
		this.deviceOemId = deviceOemId;
	}

	public String getDeviceModelNumber() {
		return deviceModelNumber;
	}

	public void setDeviceModelNumber(String deviceModelNumber) {
		this.deviceModelNumber = deviceModelNumber;
	}

	public String getReserverd() {
		return reserverd;
	}

	public void setReserverd(String reserverd) {
		this.reserverd = reserverd;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}