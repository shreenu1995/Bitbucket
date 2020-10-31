/**
 * 
 */
package com.chatak.transit.afcs.server;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 07-Jul-2017 2:23:12 PM
 * @version 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@SuppressWarnings("rawtypes")
	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			}
		}
	}
}
