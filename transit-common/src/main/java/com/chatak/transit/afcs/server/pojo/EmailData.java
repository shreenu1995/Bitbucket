package com.chatak.transit.afcs.server.pojo;

import java.sql.Timestamp;

import com.chatak.transit.afcs.server.enums.EmailStatus;

public class EmailData {

	private Long emailDataId;

	private String eventType;

	private String subject;

	private byte[] emailBody;

	private String emailAddress;

	private EmailStatus status;

	private Timestamp scheduledDate;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	public Long getEmailId() {
		return emailDataId;
	}

	public void setEmailId(Long emailDataId) {
		this.emailDataId = emailDataId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public byte[] getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(byte[] emailBody) {
		this.emailBody = emailBody;
	}

	public EmailStatus getStatus() {
		return status;
	}

	public void setStatus(EmailStatus status) {
		this.status = status;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Timestamp getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Timestamp scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(">>> EmailData [");
		builder.append(super.toString());
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", status=");
		builder.append(status == null ? "NULL" : status.toString());
		builder.append(", scheduledDate=");
		builder.append(scheduledDate);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append("]");
		return builder.toString();
	}

}
