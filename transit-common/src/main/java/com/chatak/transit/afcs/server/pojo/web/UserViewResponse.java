package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserViewResponse extends AddressRequest implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	private long id;
	private String userId;
	private String userName;
	private String passWord;
	private String status;
	private boolean currentLoginStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createdDateTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updatedDateTime;
	private String userRole;
	private String userType;
	private String firstName;
	private String lastName;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		if (createdDateTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(createdDateTime.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(createdDateTime.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(createdDateTime.toString().substring(8, 10)));
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(createdDateTime.toString().substring(11, 13)) + 6);
			time.set(Calendar.MINUTE, Integer.parseInt(createdDateTime.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(createdDateTime.toString().substring(17, 19)));
			this.createdDateTime = Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			this.createdDateTime = null;
		}
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		if (updatedDateTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(updatedDateTime.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(updatedDateTime.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(updatedDateTime.toString().substring(8, 10)));
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(updatedDateTime.toString().substring(11, 13)) + 6);
			time.set(Calendar.MINUTE, Integer.parseInt(updatedDateTime.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(updatedDateTime.toString().substring(17, 19)));
			this.updatedDateTime = Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			this.updatedDateTime = null;
		}
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean getCurrentLoginStatus() {
		return currentLoginStatus;
	}

	public void setCurrentLoginStatus(boolean currentLoginStatus) {
		this.currentLoginStatus = currentLoginStatus;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		return response.append("UserViewResponse [statusCode=").append(getStatusCode()).append(", getStatusMessage()=")
				.append(getStatusMessage()).append("]").toString();

	}

}