/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.constants;

public final class TerminalConstant {

	private TerminalConstant() {
		//Do Nothing
	}

	public static final int DEVICE_SERIAL_NUM_INITIAL_INDEX = 0;
	public static final int DEVICE_SERIAL_NUM_FINAL_INDEX = 16;
	
	public static final int DEVICE_MODEL_NUM_INITIAL_INDEX = 16;
	public static final int DEVICE_MODEL_NUM_FINAL_INDEX = 26;

	public static final int DATE_TIME_INITIAL_INDEX = 26;
	public static final int DATE_TIME_FINAL_INDEX = 45;

	public static final int CHECKSUM_INITIAL_INDEX = 45;
	public static final int CHECKSUM_FINAL_INDEX = 45;

}
