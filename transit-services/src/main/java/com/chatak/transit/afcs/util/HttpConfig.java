package com.chatak.transit.afcs.util;

import java.util.Collections;
import java.util.List;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConfig {

	private static Logger logger = Logger.getLogger(HttpConfig.class);

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 500;

	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 500;

	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (120 * 1000);

	private static HttpConfig httpConfig = new HttpConfig();

	private RestTemplate restTemplate = null;

	private HttpEntity<String> headerEntity = null;

	private HttpConfig() {

	}

	public static HttpConfig getInstance() {
		return httpConfig;
	}

	public RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restTemplate = restTemplate();
		}
		return restTemplate;
	}

	public HttpEntity<String> getHeadersEntity() {
		if (null == headerEntity) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headerEntity = new HttpEntity<>("parameters", headers);
		}
		return headerEntity;
	}

	private ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	private RestTemplate restTemplate() {

		RestTemplate httpRestTemplate = new RestTemplate(httpRequestFactory());
		List<HttpMessageConverter<?>> converters = httpRestTemplate.getMessageConverters();

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.setObjectMapper(new ObjectMapper());
			}
		}

		return httpRestTemplate;

	}

	private CloseableHttpClient httpClient() {
		CloseableHttpClient defaultHttpClient = null;
		try (PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();) {
			connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

			RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();
			defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
		} catch (NumberFormatException e) {
			logger.error("ERROR:: HttpConfig :: NumberFormatException Exception", e);
		}

		return defaultHttpClient;
	}
}
