package com.chatak.transit.afcs.server.constants;

public class Constants {
	public static final String REGEX_PHONE_VALIDATION = "[\\+0-9]{10,13}$";
	public static final String EMAIL_REGXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final int SIZE = 10;
	public static final String ALLOW_RIGHT_CLICK = "true";
	public static final Integer ROLE_NAME = 100;
	public static final Integer ROLE_DESCRIPTION = 255;
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String XLS_FILE_FORMAT = "XLS";
	public static final String CSV_FILE_FORMAT = "CSV";
	public static final String PDF_FILE_FORMAT = "PDF";
	public static final Integer REASON = 500;
	public static final String PAGE_TITLE = "Chatak AFCS";
	public static final String REPORT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String FETCH_PD_PROCESS = "/transaction/clientProcess";
	public static final String PENDING = "Pending";
	public static final String APPROVED = "Approved";
	public static final String FAILURE = "Failure";
	public static final String BASESERVICEURL = "chatak-merchant.baseServiceUrl";
	public static final String CONSUMERCLIENTID = "chatak-merchant.consumerClientId";
	public static final String CONSUMERSECRET = "chatak-merchant.consumerSecret";
	public static final String BASEADMINOAUTHSERVICEURL = "chatak-merchant.baseAdminOauthServiceUrl";
	public static final String BASEUSERNAME = "chatak-merchant.userName";
	public static final String BASEPSWD = "chatak-merchant.password";

	private Constants() {
		super();
	}

}
