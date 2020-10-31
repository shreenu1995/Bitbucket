/**
 * @author Girmiti Software
 *
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
@Table(name = "device_setup_management")
public class DeviceSetupManagement implements Serializable {

	private static final long serialVersionUID = -1840023577939172431L;

	@Id
	@SequenceGenerator(name = "sequenceDeviceSetup", sequenceName = "seq_device_setup")
	@GeneratedValue(generator = "sequenceDeviceSetup")

	@Column(name = "id")
	private Long id;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "pto_id")
	private Long ptoId;

	@Column(name = "generation_date_time")
	private Timestamp generationDatetime;

	@Column(name = "device_oem_id")
	private String deviceOemId;

	@Column(name = "processing_date_time")
	private Timestamp processDateAndtime;

	@Column(name = "device_id")
	private Long deviceId;

	public String getDeviceOemId() {
		return deviceOemId;
	}

	public void setGenerationDatetime(Timestamp generationDatetime) {
		this.generationDatetime = generationDatetime;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceOemId(String deviceOemId) {
		this.deviceOemId = deviceOemId;
	}

	public Timestamp getProcessDateAndtime() {
		return processDateAndtime;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setProcessDateAndtime(Timestamp processDateAndtime) {
		this.processDateAndtime = processDateAndtime;
	}

	public Timestamp getGenerationDatetime() {
		return generationDatetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

}
