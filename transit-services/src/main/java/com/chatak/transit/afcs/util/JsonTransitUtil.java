package com.chatak.transit.afcs.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.model.OAuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonTransitUtil {

	private static Logger logger = LoggerFactory.getLogger(JsonTransitUtil.class);

	private static final String AUTHORIZATION = "Authorization";

	private static final String AUTHORIZATION_PREFIX = "Bearer ";

	private static final String AUTHORIZATION_BASIC = "Basic ";

	public static final ObjectWriter objectWriter = new ObjectMapper().writer();

	private static final String HTTP_ERROR_CODE = "Failed with HTTP error code : ";

	private static final String CONTENT_TYPE = "content-type";

	private static final String ERROR_INVOKPOST_METHOD = "ERROR in invokePost method";

	private final String finalURL;

	private RestTemplate restTemplate = null;

	public static final String BASEOAUTHREFRESHSERVICEURL = "";

	private static String oauthRefreshToken = null;

	private static String oauthToken = null;

	private static Long tokenValidity = null;

	public static <T extends Object> T postRequest(Object request, Class<T> response, String serviceEndPoint) {
		T resultantObject = null;
		JsonTransitUtil client = new JsonTransitUtil(ConfigProperties.getPropertyValue(Constants.BASESERVICEURL),
				serviceEndPoint);

		try {

			Header[] headers = new Header[] { new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()),
					new BasicHeader("consumerClientId", ConfigProperties.getPropertyValue(Constants.CONSUMERCLIENTID)),
					new BasicHeader("consumerSecret", ConfigProperties.getPropertyValue(Constants.CONSUMERSECRET)),
					new BasicHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + getValidOAuth2Token()),

			};
			resultantObject = client.invokePost(request, response, headers);
		} catch (Exception e) {
			logger.error("ERROR: HttpClient :: postRequest method", e);
		}
		return resultantObject;
	}

	public <T> T invokePost(Object request, Class<T> response, Header[] headers) {
		logger.info("Entering :: HttpClient :: invokePost");
		try {
			ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST,
					new HttpEntity<Object>(request, setHeadersEntity(headers)), response);
			validateResponseStatusCode(resultantObject.getStatusCode().value());

			// PERF >> This method is causing object wait timeout
			logger.info("Exiting :: HttpClient :: invokePost");
			return resultantObject.getBody();

		} catch (AFCSException e) {
			logger.error(ERROR_INVOKPOST_METHOD, e);
		} catch (Exception e) {
			logger.error("ERROR in invokePost", e);
		}
		return null;
	}

	private void validateResponseStatusCode(int statusCode) throws AFCSException {
		String httpStatusCode = "HTTP Status Code: ";
		logger.info(httpStatusCode, statusCode);
		if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
			logger.info("Error Status Code : 401");
			throw new AFCSException("401");
		}
		if (statusCode != HttpStatus.SC_OK) {
			String httpErrorStatusCode = "Error Status Code : ";
			logger.info(httpErrorStatusCode, statusCode);
			throw new AFCSException(HTTP_ERROR_CODE);
		}
	}

	private HttpHeaders setHeadersEntity(Header[] headerArray) {
		HttpHeaders headers = new HttpHeaders();
		if (null != headerArray) {
			for (Header header : headerArray) {
				headers.add(header.getName(), header.getValue());
			}
		}
		return headers;
	}

	private static String refreshOAuth2Token() {
		String output = null;
		JsonTransitUtil httpClient = new JsonTransitUtil(
				ConfigProperties.getPropertyValue(Constants.BASESERVICEURL) + BASEOAUTHREFRESHSERVICEURL,
				oauthRefreshToken);
		try {
			Header[] headers = new Header[] { new BasicHeader(AUTHORIZATION, getBasicAuthValue()),
					new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()) };
			output = httpClient.invokePost(String.class, headers);
			OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
			oauthToken = apiResponse.getAccessToken();
			oauthRefreshToken = apiResponse.getRefreshTokenIn();
			tokenValidity = System.currentTimeMillis() + (apiResponse.getExpiresTypeIn() * 60);
		} catch (Exception e) {
			logger.info("Error:: HttpClient:: refreshOAuth2Token method ", e);
		}
		return oauthToken;
	}

	public <T> T invokePost(Class<T> response, Header[] headers) throws AFCSException {
		logger.info("Entering :: HttpClient :: invokePost");
		try {
			String callPostApi = "Calling POST API - ";
			logger.info(callPostApi, (finalURL));
			ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST,
					new HttpEntity<T>(null, setHeadersEntity(headers)), response);
			validateResponseStatusCode(resultantObject.getStatusCode().value());
			String covertion = "Resultant Object After convertion: ";
			logger.info(covertion, resultantObject.getBody());
			logger.info("Exiting :: HttpClient :: invokePost");
			return resultantObject.getBody();
		} catch (Exception e) {
			logger.error(ERROR_INVOKPOST_METHOD, e);
		}
		return null;
	}

	private static String getValidOAuth2Token() {
		String output = null;
		if (isValidToken()) {
			return oauthToken;
		} else {
			if (ConfigProperties.getPropertyValue(Constants.BASESERVICEURL).startsWith("https")) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {

						return new java.security.cert.X509Certificate[0];
					}

					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
						try {
							arg0[0].checkValidity();
						} catch (CertificateException e) {
							logger.info("Error:: HttpClient:: checkServerTrusted method ");
							throw new CertificateException("Certificate not valid or trusted.");
						}
					}

					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws CertificateException {
						try {
							arg0[0].checkValidity();
						} catch (CertificateException e) {
							logger.info("Error:: HttpClient:: checkClientTrusted method ");
							throw new CertificateException("Certificate not valid or trusted.");
						}
					}
				} };

				// Install the all-trusting trust manager
				getSSLContext(trustAllCerts);
			}
			JsonTransitUtil httpClient = new JsonTransitUtil(
					ConfigProperties.getPropertyValue(Constants.BASESERVICEURL),
					ConfigProperties.getPropertyValue(Constants.BASEADMINOAUTHSERVICEURL));
			try {
				Header[] headers = new Header[] { new BasicHeader(AUTHORIZATION, getBasicAuthValue()),
						new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()) };
				output = httpClient.invokePost(String.class, headers);
				OAuthToken apiResponse = new ObjectMapper().readValue(output, OAuthToken.class);
				oauthToken = apiResponse.getAccessToken();
				oauthRefreshToken = apiResponse.getRefreshTokenIn();
				tokenValidity = System.currentTimeMillis() + (apiResponse.getExpiresTypeIn() * 60);
			} catch (Exception e) {
				logger.info("Error:: HttpClient:: getValidOAuth2Token method ", e);
			}
		}
		return oauthToken;
	}

	private static void getSSLContext(TrustManager[] trustAllCerts) {
		try {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			logger.info("Error:: HttpClient:: getSSLContext method ", e);
		}
	}

	private static boolean isValidToken() {
		if (oauthToken == null || tokenValidity == null) {
			return false;
		} else if (System.currentTimeMillis() > tokenValidity) {
			oauthToken = null;
			return (null != refreshOAuth2Token());
		} else {
			return true;
		}
	}

	private static String getBasicAuthValue() {

		String basicAuth = ConfigProperties.getPropertyValue(Constants.BASEUSERNAME) + ":"
				+ ConfigProperties.getPropertyValue(Constants.BASEPSWD);
		basicAuth = AUTHORIZATION_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
		return basicAuth;
	}

	public JsonTransitUtil(String baseURIPath, String apiEndPoint) {
		this.finalURL = baseURIPath + apiEndPoint;
		this.restTemplate = HttpConfig.getInstance().getRestTemplate();
	}

}
