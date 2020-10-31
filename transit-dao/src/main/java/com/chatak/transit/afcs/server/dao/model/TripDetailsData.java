package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "trip_details")
public class TripDetailsData implements Serializable {

	@Id
	@SequenceGenerator(name = "seq_trip_details", sequenceName = "seq_trip_details")
	@GeneratedValue(generator = "seq_trip_details")

	@Column(name = "id")
	private Long id;

	@Column(name = "orginstop")
	private Long orginStop;

	@Column(name = "destinationstop")
	private Long destinationStop;

	@Column(name = "tripnumber")
	private Long tripnumber;

	@Column(name = "routecode")
	private String routecode;

	@Column(name = "trip_detail_id")
	private Long tripDetailId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrginStop() {
		return orginStop;
	}

	public void setOrginStop(Long orginStop) {
		this.orginStop = orginStop;
	}

	public Long getDestinationStop() {
		return destinationStop;
	}

	public void setDestinationStop(Long destinationStop) {
		this.destinationStop = destinationStop;
	}

	public Long getTripnumber() {
		return tripnumber;
	}

	public void setTripnumber(Long tripnumber) {
		this.tripnumber = tripnumber;
	}

	public String getRoutecode() {
		return routecode;
	}

	public void setRoutecode(String routecode) {
		this.routecode = routecode;
	}

	public Long getTripDetailId() {
		return tripDetailId;
	}

	public void setTripDetailId(Long tripDetailId) {
		this.tripDetailId = tripDetailId;
	}

}
