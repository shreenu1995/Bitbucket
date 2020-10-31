package com.afcs.web.util;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatak.transit.afcs.server.exception.AfcsHttpClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public final class AFCSHttpClient {

  private static final Logger logger = LoggerFactory.getLogger(AFCSHttpClient.class);

  private static final String CONTENT_TYPE = "Content-Type";

  private static final String AUTHORIZATION = "Authorization";

  private static final String AUTHORIZATION_PREFIX = "Bearer ";

  private static final String AUTHORIZATION_BASIC = "Basic ";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static final ObjectWriter objectWriter = new ObjectMapper().writer();
  
  private static final String HTTP_ERROR_CODE = "Failed with HTTP error code : ";

  private static final String CALL_POST = "Calling POST API - ";
  
  private static final String SUCCESS_POST = "Successfull POST API call - ";
  
  private PoolingHttpClientConnectionManager cm = null;

  private final String finalURL;

  public AFCSHttpClient(String baseURIPath, String apiEndPoint) {
    this.finalURL = baseURIPath + apiEndPoint;
    try {
      cm = new PoolingHttpClientConnectionManager();
      cm.setMaxTotal(500);
      cm.setDefaultMaxPerRoute(10);
      URI uri = new URI(finalURL);
      HttpHost host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort())
          : new HttpHost(uri.getHost());
      cm.setMaxPerRoute(new HttpRoute(host), 200);
    } catch (URISyntaxException e) {
    	String logError ="AFCSHttpClient class :: exception ";
    	logger.error(logError , e.getMessage());
    }
  }

  /**
   * Method to call GET REST Service API
   * 
   * @param response
   *          - Response Class
   * @return
   * @throws IOException
   */
  public <T extends Object> T invokeGet(Class<T> response) throws IOException {
    return invokeGetCommon(response, null);
  }

  /**
   * Method to call GET REST Service API
   * 
   * @param response
   *          - Response Class
   * @param accessToken - Access Token
   * @return
   * @throws IOException
   */
  public <T extends Object> T invokeGet(Class<T> response, String accessToken) throws IOException {
    return invokeGetCommon(response, accessToken);
  }

  private <T extends Object> T invokeGetCommon(Class<T> response, String accessToken)
      throws IOException {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = getHttpPoolManager();
    CloseableHttpClient httpClient =
        HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
    CloseableHttpResponse httpResponse = null;
    try {
      String logInfo = "Calling GET API - ";
      logger.info(logInfo , (finalURL));
      HttpGet getRequest = new HttpGet(finalURL);
      getRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if (accessToken != null) {
        getRequest.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
      }
      httpResponse = httpClient.execute(getRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode != HttpStatus.SC_OK) {
        throw new AfcsHttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      String errorInfo = "Successfull GET API call - ";
      logger.info(errorInfo ,(finalURL));
      return resultantObject;
    } catch (Exception e) {
      logger.error("ERROR in calling GET API " + (finalURL), e);
    } finally {
      closeResources(httpClient, httpResponse, poolingHttpClientConnectionManager);
    }
    String errorInfo = "ERROR in calling GET API and rerurning NULL ";
    logger.error(errorInfo , (finalURL));
    return null;
  }

  /**
   * Method to call POST REST Service API
   * 
   * @param request
   *          - Request Pay Load object
   * @param response
   *          - Response Class
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response) {

    return invokePostCommon(request, response, null);
  }

  /**
   * @param request
   * @param response
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response, boolean basicAuth,
      String serviceToken) {
    if (basicAuth) {
      return invokeBasicAuth(request, response, serviceToken);
    } else {
      return invokePostCommon(request, response, serviceToken);
    }

  }

  /**
   * Method to call POST REST Service API
   * 
   * @param request
   *          - Request Pay Load object
   * @param response
   *          - Response Class
   * @param accessToken - Access Token
   * @return
   */
  public <T> T invokePost(Object request, Class<T> response, String accessToken) {
    return invokePostCommon(request, response, accessToken);
  }

  private <T> T invokePostCommon(Object request, Class<T> response, String accessToken) {

    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = getHttpPoolManager();
    CloseableHttpClient httpClient =
        HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info(CALL_POST , (finalURL));
      HttpPost postReq = new HttpPost(finalURL);
      postReq.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if (accessToken != null) {
        postReq.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
      }
      postReq.setEntity(new StringEntity(objectWriter.writeValueAsString(request)));
      httpResponse = httpClient.execute(postReq);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode != HttpStatus.SC_OK) {
        throw new AfcsHttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info(SUCCESS_POST , (finalURL));
      return resultantObject;
    } catch (Exception ex) {
      logger.error("ERROR:: AFCSHttpClient:: invokePost method", ex);
    } finally {
      closeResources(httpClient, httpResponse, poolingHttpClientConnectionManager);
    }
    String info = "ERROR in calling POST API and rerurning NULL ";
    logger.error(info , (finalURL));
    return null;
  }


  private <T> T invokeBasicAuth(Object request, Class<T> response, String accessToken) {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = getHttpPoolManager();
    CloseableHttpClient httpClient =
        HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
    CloseableHttpResponse httpResponse = null;
    try {
      logger.info(CALL_POST , (finalURL));
      HttpPost postRequest = new HttpPost(finalURL);
      postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      if (accessToken != null) {
        postRequest.addHeader(AUTHORIZATION, AUTHORIZATION_BASIC + accessToken);
      }
      postRequest.setEntity(new StringEntity(objectWriter.writeValueAsString(request)));
      httpResponse = httpClient.execute(postRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode != HttpStatus.SC_OK) {
        throw new AfcsHttpClientException(HTTP_ERROR_CODE, statusCode);
      }

      T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      logger.info(SUCCESS_POST , (finalURL));
      return resultantObject;
    } catch (Exception e) {
      logger.error("ERROR:: AFCSHttpClient:: invokePost method", e);
    } finally {
      closeResources(httpClient, httpResponse, poolingHttpClientConnectionManager);
    }
    String info = "ERROR in calling POST API and rerurning NULL ";
    logger.error(info , (finalURL));
    return null;
  }

  /**
   * @return
   */
  private PoolingHttpClientConnectionManager getHttpPoolManager() {
    int maxTotal = 300;
    int maxPerRoute = 30;
    int urlConnectionTimout = 30000;
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
        new PoolingHttpClientConnectionManager();
    poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
    poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

    SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(urlConnectionTimout).build();
    poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
    return poolingHttpClientConnectionManager;
  }

  private void closeResources(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse,
      PoolingHttpClientConnectionManager cm) {
    try {
      if (null != httpResponse) {
        httpResponse.close();
      }
      if (null != httpClient) {
        httpClient.close();
      }
      cm.close();
    } catch (Exception e) {
      logger.error("ERROR:: AFCSHttpClient:: closeResources method while closing the httpClient",
          e);
    }
  }
  
  public <T> T invokePost(Object request, Class<T> response, Header[] headers) {

    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    T resultantObject = null;
    try {
      HttpPost postRequest = new HttpPost(finalURL);
      postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
      postRequest.setHeaders(headers);
      httpClient = HttpClients.custom().setConnectionManager(cm).build();

      String jsonRequest = objectWriter.writeValueAsString(request);
      postRequest.setEntity(new StringEntity(jsonRequest));
      httpResponse = httpClient.execute(postRequest);
      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (statusCode != HttpStatus.SC_OK) {
        throw new AfcsHttpClientException(HTTP_ERROR_CODE ,statusCode);
      }
      if (response.newInstance() instanceof java.lang.String) {
        resultantObject =
            (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
        resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
      } else {
        resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
      }
      return resultantObject;
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
    	String info ="AFCSHttpClient class :: invokePost method :: Exception ";
    	logger.error(info, e.getMessage());
    } finally {
      try {
        if(httpResponse != null) {
          httpResponse.close();
        }
        if (null != httpClient) {
          httpClient.close();
        }
      } catch (IOException e) {
    	  String info = "AFCSHttpClient class :: invokePost method :: Exception ";
    	  logger.error(info , e.getMessage());
      }
    }
    return null;
}

  
  public static Header[] getHeaders(String token){
    return new Header[] {
        new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()),
        new BasicHeader(AUTHORIZATION, getBasicAuthValue(token))};
  }
  
  private static String getBasicAuthValue(String token) {
    return AUTHORIZATION_BASIC + token;
  }

}
