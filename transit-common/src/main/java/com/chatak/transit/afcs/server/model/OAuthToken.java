/**
 * 
 */
package com.chatak.transit.afcs.server.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 10-Mar-2015 10:37:27 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8760650517781245395L;

	private String accessToken;

	private String refreshTokenIn;

	private String tokenTypeIn;

	private Integer expiresTypeIn;

	private String value;

	private Integer expiresIn;

	private Long expiration;

	private String tokenType;

	private List<String> scope;

	private transient Map additionalInformation;

	private Boolean expired;

	private OAuthToken refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshTokenIn() {
		return refreshTokenIn;
	}

	public void setRefreshTokenIn(String refreshTokenIn) {
		this.refreshTokenIn = refreshTokenIn;
	}

	public String getTokenTypeIn() {
		return tokenTypeIn;
	}

	public void setTokenTypeIn(String tokenTypeIn) {
		this.tokenTypeIn = tokenTypeIn;
	}

	public Integer getExpiresTypeIn() {
		return expiresTypeIn;
	}

	public void setExpiresTypeIn(Integer expiresTypeIn) {
		this.expiresTypeIn = expiresTypeIn;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		setAccessToken(value);
		this.value = value;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		setExpiresTypeIn(expiresIn);
		this.expiresIn = expiresIn;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		setTokenTypeIn(tokenType);
		this.tokenType = tokenType;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	public Map getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(Map additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public OAuthToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(OAuthToken refreshToken) {
		this.refreshToken = refreshToken;
		if (refreshToken != null) {
			setRefreshTokenIn(refreshToken.getValue());
		}
	}
}
