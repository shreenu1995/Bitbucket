/**
 * @author Girmiti Software
 */
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
@Table(name = "operator_session_management")
public class AdminSessionManagement implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "seq_admin_session_management_table", sequenceName = "seq_admin_session_management_table")
	@GeneratedValue(generator = "seq_admin_session_management_table")

	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "generation_date_time")
	private Timestamp generateDateAndTime;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "employee_id")
	private String empId;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "process_date_time")
	private Timestamp processDateAndtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Timestamp getGenerateDateAndTime() {
		return generateDateAndTime;
	}

	public void setGenerateDateAndTime(Timestamp generateDateAndTime) {
		this.generateDateAndTime = generateDateAndTime;
	}

	public Timestamp getProcessDateAndtime() {
		return processDateAndtime;
	}

	public void setProcessDateAndtime(Timestamp processDateAndtime) {
		this.processDateAndtime = processDateAndtime;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

}
