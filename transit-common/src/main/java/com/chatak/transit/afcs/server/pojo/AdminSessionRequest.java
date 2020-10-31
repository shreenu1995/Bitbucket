package com.chatak.transit.afcs.server.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chatak.transit.afcs.server.constants.AdminSessionConstants;
import com.chatak.transit.afcs.server.constants.ServerConstants;

public class AdminSessionRequest implements Serializable {

	private static final long serialVersionUID = -1881410074111306427L;
	private static final Logger logger = LoggerFactory.getLogger(AdminSessionRequest.class);

	private String userId;
	private String empId;
	private Long ptoId;
	private String deviceId;
	private String transactionId;
	private Timestamp dateTime;
	private String reservedSession;
	private String checkSum;

	public void parseAdminSessionRequest(String data) {

		// Ticket Information Data Parsing
		userId = data.substring(AdminSessionConstants.ADMIN_SESSION_USER_ID_INITIAL_INDEX,
				AdminSessionConstants.ADMIN_SESSION_USER_ID_FINAL_INDEX).trim();
		empId = data.substring(AdminSessionConstants.ADMIN_SESSION_EMPLOYEE_ID_INITIAL_INDEX,
				AdminSessionConstants.ADMIN_SESSION_EMPLOYEE_ID_FINAL_INDEX).trim();
		ptoId = Long.valueOf(data.substring(AdminSessionConstants.ADMIN_SESSION_PTO_ID_INITIAL_INDEX,
				AdminSessionConstants.ADMIN_SESSION_PTO_ID_FINAL_INDEX).trim());

		deviceId = data.substring(AdminSessionConstants.ADMIN_SESSION_DEVICE_ID_INITIAL_INDEX,
				AdminSessionConstants.ADMIN_SESSION_DEVICE_ID_FINAL_INDEX).trim();

		transactionId = data.substring(AdminSessionConstants.ADMIN_SESSION_TRANSACTION_ID_INITIAL_INDEX,
				AdminSessionConstants.ADMIN_SESSION_TRANSACTION_ID_FINAL_INDEX).trim();

		dateTime = Timestamp.valueOf(data
				.substring(AdminSessionConstants.DATE_TIME_INITIAL_INDEX, AdminSessionConstants.DATE_TIME_FINAL_INDEX)
				.trim());

	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) throws ParseException {

		if (dateTime.length() == ServerConstants.ADMIN_SESSION_DATA_LENGTH) {
			logger.info("Inside set date time()");
			logger.info(dateTime);
			SimpleDateFormat receivedTimeFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			SimpleDateFormat requiredTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			this.dateTime = Timestamp.valueOf(requiredTimeFormat.format(receivedTimeFormat.parse(dateTime)));
			String logInfo = this.dateTime.toString();
			logger.info(logInfo);
		}
	}

	public String getReservedSession() {
		return reservedSession;
	}

	public void setReservedSession(String reservedSession) {
		this.reservedSession = reservedSession;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {

		StringBuilder request = new StringBuilder();

		return request.append("AdminSessionRequest [ userId=").append(userId).append(", empId=").append(empId)
				.append(", ptoOperationId=").append(ptoId).append(", deviceId=").append(deviceId)
				.append(", transactionId=").append(transactionId).append(", dateTime=").append(dateTime)
				.append(", reservedSession=").append(reservedSession).append(", checkSum=").append(checkSum).append("]")
				.toString();

	}

}
