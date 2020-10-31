package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "discount_master")
@IdClass(value = IdForDiscount.class)
public class DiscountMaster implements Serializable {

	private static final long serialVersionUID = -1061523050259053018L;

	@Id
	@SequenceGenerator(name = "discount_id_seq", sequenceName = "discount_id_seq")
	@GeneratedValue(generator = "discount_id_seq")
	@Column(name = "discount_id")
	private Long discountId;

	@Column(name = "organization_id")
	private Long organizationId;

	@Column(name = "pto_Id")
	private Long ptoId;

	@Column(name = "discount_type")
	private String discountType;

	@Column(name = "discount_name")
	private String discountName;

	@Column(name = "route_stage_station_difference")
	private Long routeStageStationDifference;

	@Column(name = "discount")
	private Long discount;

	@Column(name = "status")
	private String status;

	@Column(name = "reason")
	private String reason;

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public Long getRouteStageStationDifference() {
		return routeStageStationDifference;
	}

	public void setRouteStageStationDifference(Long routeStageStationDifference) {
		this.routeStageStationDifference = routeStageStationDifference;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}

class IdForDiscount implements Serializable {

	private static final long serialVersionUID = -3229506170759136584L;
	Long discountId;

}
