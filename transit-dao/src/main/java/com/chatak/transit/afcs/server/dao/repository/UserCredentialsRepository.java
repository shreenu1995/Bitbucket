package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.chatak.transit.afcs.server.dao.model.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
	@Transactional
	@Modifying
	@Query("UPDATE UserCredentials c SET c.userStatus = :userStatus, c.reason = :reason WHERE c.userID = :userID")
	int updateStatus(@Param("userStatus") String b, @Param("userID") String userID, @Param("reason") String reason);

	@Transactional
	@Modifying
	@Query("UPDATE UserCredentials c SET c.passWord = :pass, c.previousPasswords=:previousPasswords, c.loginMode=:loginMode WHERE c.userID = :userID")
	int updatePassword(@Param("pass") String pass, @Param("userID") String userID, @Param("previousPasswords") String previousPasswords,@Param("loginMode") Long loginMode);

	boolean existsByEmail(String userId);

	boolean existsByEmailAndUserStatus(String userId,String status);

	boolean existsByUserID(String userId);

	boolean existsByAdminUserId(String userId);

	boolean existsByPassWordAndEmail(String pass, String userId);

	UserCredentials findByUserIDAndPassWord(String userId, String password);

	boolean existsByUserIDAndUserStatus(String userId, boolean status);

	boolean existsByUserIDAndOrganizationId(String userId, String organizationId);

	UserCredentials findByEmail(String userId);

	boolean existsByUserNameAndUserStatusNotLike(String userName, String userStatus);
	
	UserCredentials findByUserID(String userId);

	UserCredentials findByEmailAndUserStatusNotLike(String userId, String value);
	
	UserCredentials findByEmailAndUserStatusLike(String userId, String value);

	boolean existsByEmailAndUserStatusNotLike(String userId, String value);

}
