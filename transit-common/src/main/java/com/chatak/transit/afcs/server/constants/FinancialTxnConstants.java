package com.chatak.transit.afcs.server.constants;

public class FinancialTxnConstants {

	private FinancialTxnConstants() {

		// Do nothing
	}

	public static final int FINANCIAL_TXN_DATA_LENGTH = 1024;
	public static final String TXN_TYPE_TT01 = "TT01";
	public static final String TXN_TYPE_TT02 = "TT02";
	public static final String TXN_TYPE_TL01 = "TL01";
	public static final String TXN_TYPE_TP01 = "TP01";
	public static final String TXN_TYPE_CP01 = "CP01";
	public static final String TXN_TYPE_TK01 = "TK01";

	public static final int USERID_INITIAL_INDEX = 0;
	public static final int USERID_FINAL_INDEX = 10;

	public static final int EMPLOYEE_ID_INITIAL_INDEX = 10;
	public static final int EMPLOYEE_ID_FINAL_INDEX = 20;

	public static final int PTO_OPERATION_ID_INITIAL_INDEX = 20;
	public static final int PTO_OPERATION_ID_FINAL_INDEX = 32;

	public static final int DEVICE_ID_INITIAL_INDEX = 32;
	public static final int DEVICE_ID_FINAL_INDEX = 44;

	public static final int FINANCIAL_TXN_TYPE_INITIAL_INDEX = 44;
	public static final int FINANCIAL_TXN_TYPE_FINAL_INDEX = 48;

	public static final int DATE_TIME_INITIAL_INDEX = 48;
	public static final int DATE_TIME_FINAL_INDEX = 67;

	public static final int RESERVED_SESSION_INITIAL_INDEX = 67;
	public static final int RESERVED_SESSION_FINAL_INDEX = 127;

	public static final int NO_OF_FIELDS_INITIAL_INDEX = 127;
	public static final int NO_OF_FIELDS_FINAL_INDEX = 129;

	public static final int FIELDS_INITIAL_INDEX = 129;
	public static final int FIELDS_FINAL_INDEX = 829;

	public static final int SHIFT_CODE_INITIAL_INDEX = 829;
	public static final int SHIFT_CODE_FINAL_INDEX = 833;

	public static final int SHIFT_BATCH_NO_INITIAL_INDEX = 833;
	public static final int SHIFT_BATCH_NO_FINAL_INDEX = 839;

	public static final int TRIP_NO_INITIAL_INDEX = 839;
	public static final int TRIP_NO_FINAL_INDEX = 843;

	public static final int PAYMENT_HOST_BATCH_NO_INITIAL_INDEX = 843;
	public static final int PAYMENT_HOST_BATCH_NO_FINAL_INDEX = 849;

	public static final int PAYMENT_HOST_TERMINAL_ID_INITIAL_INDEX = 849;
	public static final int PAYMENT_HOST_TERMINAL_ID_FINAL_INDEX = 861;

	public static final int RESERVED_FINANCE_INITIAL_INDEX = 861;
	public static final int RESERVED_FINANCE_FINAL_INDEX = 905;

	public static final int SOFTWARE_VERSION_INITIAL_INDEX = 905;
	public static final int SOFTWARE_VERSION_FINAL_INDEX = 919;

	public static final int MASTER_VERSION_INITIAL_INDEX = 919;
	public static final int MASTER_VERSION_FINAL_INDEX = 927;

	public static final int DEVICE_SERIAL_NO_INITIAL_INDEX = 927;
	public static final int DEVICE_SERIAL_NO_FINAL_INDEX = 943;

	public static final int DEVICE_MODEL_NO_INITIAL_INDEX = 943;
	public static final int DEVICE_MODEL_NO_FINAL_INDEX = 953;

	public static final int DEVICE_COMPONENT_VERSION_INITIAL_INDEX = 953;
	public static final int DEVICE_COMPONENT_VERSION_FINAL_INDEX = 973;

	public static final int RESERVED_VERSION_INITIAL_INDEX = 973;
	public static final int RESERVED_VERSION_FINAL_INDEX = 1023;

	public static final int CHECKSUM_INITIAL_INDEX = 1023;
	public static final int CHECKSUM_FINAL_INDEX = 1023;

	public static final int DATA_OBJECT_LENGTH = 14;

	public static final int TXNTYPE_INITIAL_INDEX = 0;
	public static final int TXNTYPE_FINAL_INDEX = 4;

	public static final int TXNCOUNT_INITIAL_INDEX = 4;
	public static final int TXNCOUNT_FINAL_INDEX = 8;

	public static final int TXNAMOUNT_INITIAL_INDEX = 8;
	public static final int TXNAMOUNT_FINAL_INDEX = 14;

}
