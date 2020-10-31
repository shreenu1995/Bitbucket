package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ENTITY_FEATURE")
public class EntityFeature implements Serializable {

	private static final long serialVersionUID = -5252155627821563956L;

	@Id
	@SequenceGenerator(name = "seq_entity_feature_id", sequenceName = "seq_entity_feature_id")
	@GeneratedValue(generator = "seq_entity_feature_id")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ENTITY_ID")
	private Long entityId;

	@Column(name = "FEATURE_ID")
	private Long featureId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

}
