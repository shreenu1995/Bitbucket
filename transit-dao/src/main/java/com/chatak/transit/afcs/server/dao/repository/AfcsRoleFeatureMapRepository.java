package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chatak.transit.afcs.server.dao.model.AfcsRoleFeatureMap;

public interface AfcsRoleFeatureMapRepository extends JpaRepository<AfcsRoleFeatureMap, Long> {

	List<AfcsRoleFeatureMap> findByRoleId(Long roleId);
}
