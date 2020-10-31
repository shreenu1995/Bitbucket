package com.afcs.web.controller;

import java.io.IOException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public final class Properties extends PropertyPlaceholderConfigurer {

	@Override
	  public java.util.Properties mergeProperties() throws IOException {
	    java.util.Properties prop = super.mergeProperties();
	    PropertiesContainer.getInstance().setPropsExported(prop);
	    return prop;
	  }

	  /**
	   * Get the value of given key from the property file
	   * 
	   * @param key
	   * @return
	   */
	  public static String getProperty(final String key) {
	    String value = PropertiesContainer.getInstance().getPropsExported().getProperty(key);
	    return ((value == null) ? "" : value);
	  }
	  
	  /**
	   * Get the value of given key from the property file
	   * 
	   * @param key
	   * @param defaultValue
	   * @return
	   */
	  public static String getProperty(final String key, final String defaultValue) {
	    String value = PropertiesContainer.getInstance().getPropsExported().getProperty(key);
	    return ((value == null) ? defaultValue : value);
	  }

	  public static void mergeProperties(java.util.Properties propsExportedLocal) {
	    PropertiesContainer.getInstance().setPropsExported(propsExportedLocal);
	  }
}
