/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.constants;

import java.sql.Timestamp;
import java.time.Instant;

public class ServerConstants {

	protected ServerConstants() {
		super();
	}

	public static final int TIME_STAMP_LENGTH = 20;
	public static final int SETUP_DATA_LENGTH = 128;
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String SHIFTEND = "Shiftend";
	public static final long COUNT_INDEX = 1l;
	public static final int COUNT = 1;
	public static final char BIT = '1';
	public static final String CHAR_ENCODING_CONS = "UTF-8";
	public static final int ADMIN_SESSION_DATA_LENGTH = 128;
	public static final String PRIVILEGE_BIT_INDEX = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	public static final String FINANCIAL_TXN_SHIFTEND = "F001";
	public static final String FINANCIAL_TXN_TRIPEND = "F002";
	public static final String USER_HOME = "user.home";
	public static final String FILE_SEPERATOR = "file.separator";
	public static final String SOFTWARE_MANAGEMENT = "softwaremanagement";
	protected static final Timestamp TIME = Timestamp.from(Instant.now());
	public static final int FILE_DATA_LENGTH = 128;
	public static final String VERSION = "01.00.00";
	public static final String STATUS = "OK";
	public static final int ONE = 1;
	public static final int FOUR = 4;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int TWELVE = 12;
	public static final int THIRTY_FIVE = 35;
	public static final String YES="yes";
	public static final String ID_NOT_EXIST="Id does not exist";
	public static final String AFCS="afcs";
	public static final String MASTERS="masters";
	public static final String MASTERS_ZIP="masters.zip";
	public static final String TOMCAT="tomcat";
	public static final String WEBAPPS="webapps";
	public static final String ROOT="ROOT";


}
