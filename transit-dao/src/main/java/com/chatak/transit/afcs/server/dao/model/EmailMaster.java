package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "email_service")
public class EmailMaster implements Serializable {

	private static final long serialVersionUID = 8977391445629704663L;

	@Id
	@SequenceGenerator(name = "email_service_id", sequenceName = "email_service_id")
	@GeneratedValue(generator = "email_service_id")

	@Column(name = "email_id")
	private Long emailId;

	@Column(name = "event_type")
	private String eventType;

	@Column(name = "subject")
	private String subject;

	@Column(name = "email_body")
	private byte[] emailBody;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "status")
	private String status;

	@Column(name = "scheduled_date")
	private Timestamp scheduledDate;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public byte[] getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(byte[] emailBody) {
		this.emailBody = emailBody;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Timestamp scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}
