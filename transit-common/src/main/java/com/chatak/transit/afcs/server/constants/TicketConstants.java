package com.chatak.transit.afcs.server.constants;

public final class TicketConstants {

	private TicketConstants() {
		// Do nothing
	}

	public static final int TICKET_TXN_DATA_LENGTH = 384;

	public static final String TICKET_CASH_PAYMENT_MODE = "P0";
	public static final String TICKET_TRANSACTION_ID = "P001";

	// TICKET INFORMATION INDEXES
	public static final int TICKET_NUM_INITIAL_INDEX = 0;
	public static final int TICKET_NUM_FINAL_INDEX = 16;

	public static final int TRANSACTION_ID_INITIAL_INDEX = 16;
	public static final int TRANSACTION_ID_FINAL_INDEX = 20;

	public static final int TICKET_DATE_TIME_INITIAL_INDEX = 20;
	public static final int TICKET_DATE_TIME_FINAL_INDEX = 39;

	public static final int TICKET_FARE_INITIAL_INDEX = 39;
	public static final int TICKET_FARE_FINAL_INDEX = 51;

	public static final int TICKET_FARE_OPTIONAL_POSITIVE_AMOUNT_INITIAL_INDEX = 51;
	public static final int TICKET_FARE_OPTIONAL_POSITIVE_AMOUNT_FINAL_INDEX = 63;

	public static final int TICKET_FARE_OPTIONAL_NEGATIVE_AMOUNT_INITIAL_INDEX = 63;
	public static final int TICKET_FARE_OPTIONAL_NEGATIVE_AMOUNT_FINAL_INDEX = 75;

	public static final int TICKET_OPERATION_DATE_INITIAL_INDEX = 75;
	public static final int TICKET_OPERATION_DATE_FINAL_INDEX = 85;

	public static final int TICKET_PAYMENT_MODE_INITIAL_INDEX = 85;
	public static final int TICKET_PAYMENT_MODE_FINAL_INDEX = 93;

	public static final int TICKET_ORIGIN_STOP_INITIAL_INDEX = 93;
	public static final int TICKET_ORIGIN_STOP_FINAL_INDEX = 97;

	public static final int TICKET_DEST_STOP_INITIAL_INDEX = 97;
	public static final int TICKET_DEST_STOP_FINAL_INDEX = 101;

	public static final int TICKET_PASSENGER_COUNT_INITIAL_INDEX = 101;
	public static final int TICKET_PASSENGER_COUNT_FINAL_INDEX = 105;

	public static final int RESERVED_TICKET_INITIAL_INDEX = 105;
	public static final int RESERVED_TICKET_FINAL_INDEX = 127;

	// DEVICE INFORMATION INDEXES
	public static final int PTO_OPERATION_ID_INITIAL_INDEX = 127;
	public static final int PTO_OPERATION_ID_FINAL_INDEX = 139;

	public static final int DEVICE_ID_INITIAL_INDEX = 139;
	public static final int DEVICE_ID_FINAL_INDEX = 151;

	public static final int RESERVERD_DEVICE_INITIAL_INDEX = 151;
	public static final int RESERVERD_DEVICE_FINAL_INDEX = 171;

	// CREW INFORMATION INDEXES
	public static final int CONDUCTOR_EMP_ID_INITIAL_INDEX = 171;
	public static final int CONDUCTOR_EMP_ID_FINAL_INDEX = 181;

	public static final int DRIVER_EMP_ID_INITIAL_INDEX = 181;
	public static final int DRIVER_EMP_ID_FINAL_INDEX = 191;

	public static final int SHIFT_CODE_INITIAL_INDEX = 191;
	public static final int SHIFT_CODE_FINAL_INDEX = 195;

	public static final int SHIFT_BATCH_NUM_INITIAL_INDEX = 195;
	public static final int SHIFT_BATCH_NUM_FINAL_INDEX = 201;

	public static final int TRIP_NUM_INITIAL_INDEX = 201;
	public static final int TRIP_NUM_FINAL_INDEX = 205;

	public static final int ROUTE_CODE_INITIAL_INDEX = 205;
	public static final int ROUTE_CODE_FINAL_INDEX = 215;

	public static final int TRANSPORT_ID_INITIAL_INDEX = 215;
	public static final int TRANSPORT_ID_FINAL_INDEX = 227;

	public static final int CURRENT_STOP_ID_INITIAL_INDEX = 227;
	public static final int CURRENT_STOP_ID_FINAL_INDEX = 231;

	public static final int RESERVED_CREW_INITIAL_INDEX = 231;
	public static final int RESERVED_CREW_FINAL_INDEX = 251;

	// CARD INFORMATION INDEXES
	public static final int CARD_NUM_INITIAL_INDEX = 251;
	public static final int CARD_NUM_FINAL_INDEX = 271;

	public static final int CARD_BALANCE_INITIAL_INDEX = 271;
	public static final int CARD_BALANCE_FINAL_INDEX = 283;

	public static final int CARD_EXP_DATE_INITIAL_INDEX = 283;
	public static final int CARD_EXP_DATE_FINAL_INDEX = 287;

	public static final int RESERVED_CARD_INITIAL_INDEX = 287;
	public static final int RESERVED_CARD_FINAL_INDEX = 307;

	// RECONCILIATION INFORMATION INDEXES
	public static final int PAYMENT_TXN_UNIQUE_ID_INITIAL_INDEX = 307;
	public static final int PAYMENT_TXN_UNIQUE_ID_FINAL_INDEX = 323;

	public static final int PAYMENT_TXN_TERMINAL_UID_INITIAL_INDEX = 323;
	public static final int PAYMENT_TXN_TERMINAL_UID_FINAL_INDEX = 335;

	public static final int RESERVED_RECONCILIATION_INITIAL_INDEX = 335;
	public static final int RESERVED_RECONCILIATION_DATE_FINAL_INDEX = 355;

	// PASS INFORMATION INDEXES
	public static final int PASS_TYPE_INITIAL_INDEX = 355;
	public static final int PASS_TYPE_FINAL_INDEX = 357;
	
	//OPERATOR INFORMATION INDEXES
	public static final int OPERATOR_INITIAL_INDEX = 358;
	public static final int OPERATOR_FINAL_INDEX = 363;

	public static final int RESERVED_PASS_INITIAL_INDEX = 363;
	public static final int RESERVED_PASS_FINAL_INDEX = 383;

	// CHECKSUM INFORMATION INDEXES
	public static final int CHECKSUM_INDEX = 383;
	
	public static final String FINISHED_TICKET_DATE_TIME = "finished parsing ticket date and time";
	public static final String TICKET_NO = "Ticket Number: ";
	public static final String TXN_ID = "Transaction Id: ";
	public static final String TICKET_DATE_TIME = "Ticket Date and Time: ";
	public static final String TICKET_FARE = "Ticket Fare: ";
	public static final String TICKET_OPERATION_DATE = "Ticket Operation Date: ";
	
	public static final String TICKET_PAYMENT_MODE = "Ticket Payment Mode: ";
	public static final String TICKET_ORIGIN_STOP = "Ticket Origin Stop: ";
	public static final String TICKET_DESTINATION_STOP = "Ticket Destination Stop: ";
	public static final String TICKET_PASSANGER_COUNT = "Ticket Ticket Passenger Count: ";
	public static final String TICKET_RESERVED_INFO = "Ticket Reserved Info: ";
	
	public static final String PTO_OPERATION_ID = "Pto Operation Id: ";
	public static final String DEVICE_ID = "Device Id: ";
	public static final String RESERVED_DEVICE_INFO = "Reserved Device Info: ";
	
	public static final String CONDUCTOR_EMP_ID = "Conductor Employee Id: ";
	public static final String DRIVER_EMP_ID = "Driver Employee Id: ";
	public static final String SHIFT_ID = "Shift Id: ";
	public static final String TRIP_ID = "Trip Id: " ;
	public static final String ROUTE_CODE = "Route Code: " ;
	
	public static final String VEHICLE_NO = "Vehicle Number: ";
	public static final String CURRENT_STOP_ID = "Current Stop Id: ";
	public static final String RESERVED_CREW_INFO = "Reserved Crew Info: ";
	
	public static final String CARD_NO = "Card Number: ";
	public static final String CARD_BALANCE = "Card Balance: ";
	public static final String CARD_EXPIRY = "card Expiry Date: ";
	public static final String CARD_RESERVED_INFO = "Card Reserved Infor: ";
	
	public static final String PAY_TXN_ID = "Payment Transaction Unique Id: ";
	public static final String PAY_TXN_TERMINAL_ID = "Payment Transaction Terminal Uid: ";
    public static final String RESERVED_RECONCIL_INFO = "Reserved Reconciliation Info: ";
    
    public static final String PASS_TYPE = "Pass Type: ";
    public static final String RESERVED_PASS_INFO = "Reserveed Pass Info: ";
    
    public static final String CHECKSUM = "Checksum: ";
    public static final String OPERATOR = "operatorId: ";

}
