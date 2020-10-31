package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.PtoMaster;

public interface PtoMasterRepository extends JpaRepository<PtoMaster, Long> {

	public boolean existsByPtoName(String ptoName);

	List<PtoMaster> findByPtoName(String ptoName);

	public boolean existsByPtoNameAndStatusNotLike(String ptoName, String status);

	public List<PtoMaster> findByOrgIdAndStatusNotLike(Long orgId, String status);

	public PtoMaster findByPtoMasterId(Long ptoId);

	public List<PtoMaster> findByOrgIdAndStatusLike(Long orgId, String status);

	public List<PtoMaster> findByPtoMasterIdAndStatusLike(Long valueOf, String status);

	public List<PtoMaster> findByPtoNameAndStatusLike(String ptoName, String status);

	public boolean existsByPtoMasterId(Long ptoId);

	public List<PtoMaster> findByPtoMasterIdAndStatusNotLike(Long ptoMasterId, String value);

}
