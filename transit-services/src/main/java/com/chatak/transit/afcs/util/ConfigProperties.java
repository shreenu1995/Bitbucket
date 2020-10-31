package com.chatak.transit.afcs.util;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigProperties {
    
	private static Logger logger = LoggerFactory.getLogger(ConfigProperties.class);
	private ConfigProperties() {
		
	}
    private static Properties prop;
     
    static{
     
        try {
            prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e ) {
        	logger.error("ConfigProperties", e);
        }
    }
     
    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }
 }