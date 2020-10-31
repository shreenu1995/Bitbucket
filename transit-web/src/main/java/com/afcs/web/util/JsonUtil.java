package com.afcs.web.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class JsonUtil {
	
	@Autowired
	Environment properties;

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String convertObjectToJSON(Object object) throws JsonProcessingException {
		String input = "";
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		input = objectWriter.writeValueAsString(object);
		return input;
	}

	public static Object convertJSONToObject(String jsonData, Class<?> c)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonData, c);
	}

	public <T extends Object> T postRequest(Object request, Class<T> response, String serviceEndPoint) {
		T resultantObject = null;

		try {
			resultantObject = getAFCSHttpClientObj(serviceEndPoint).invokePost(request, response, getHeaders());
		} catch (Exception e) {
			String info = "JsonUtil class :: postRequest method :: Exception ";
			logger.error(info,e.getMessage());
		}
		return resultantObject;
	}

	/**
	 * @param serviceEndPoint
	 * @return
	 */
	public <T extends Object> T postRequest(Class<T> response, String serviceEndPoint) {
		T resultantObject = null;
		try {
			resultantObject = getAFCSHttpClientObj(serviceEndPoint).invokePost(null, response, getHeaders());
		} catch (Exception e) {
			String info = "JsonUtil class :: postRequest method :: Exception ";
			logger.error(info,e.getMessage());

		}
		return resultantObject;

	}

	/**
	 * @param serviceEndPoint
	 * @return
	 */
	public AFCSHttpClient getAFCSHttpClientObj(String serviceEndPoint) {
		return new AFCSHttpClient(properties.getProperty("afcs.web.service.url"), serviceEndPoint);
	}

	public Header[] getHeaders() {
    return new Header[] {
        new BasicHeader("content-type", ContentType.APPLICATION_JSON.getMimeType())};
  }
}
