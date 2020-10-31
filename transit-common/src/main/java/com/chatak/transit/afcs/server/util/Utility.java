package com.chatak.transit.afcs.server.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;

import com.chatak.transit.afcs.server.constants.ServerConstants;

public class Utility {
	private static Logger logger = LoggerFactory.getLogger(Utility.class);

	private Utility() {
		// Do nothing
	}

	public static void fetchFile(HttpServletResponse response, String path) throws IOException {

		File file = new File(path);
		if (!file.exists()) {
			response.getOutputStream().write("File Not Found!!!".getBytes(Charset.forName("UTF-8")));
			response.getOutputStream().close();
		} else {
			response.setContentType(new StringBuilder().append("application/zip;charset=UTF-8").toString());
			response.setHeader("Content-Disposition",
					new StringBuilder().append("inline;filename=").append("masters.zip").toString());
			response.setContentLength((int) file.length());
			try(InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			} catch (Exception e) {
				logger.error("Exception", e);
			}
		}
	}

	public static String formSupportedPrevileges(List<Integer> bitPosition, String supportedPrevilegesBitValue) {
		char[] supportedPrevilegesChars = supportedPrevilegesBitValue.toCharArray();
		for (Iterator<Integer> iterator = bitPosition.iterator(); iterator.hasNext();) {
			Integer position = iterator.next();
			supportedPrevilegesChars[(position) - 1] = ServerConstants.BIT;
			supportedPrevilegesBitValue = String.valueOf(supportedPrevilegesChars);
		}
		return supportedPrevilegesBitValue;
	}
	
	 /**
	   * Method to copy the the list of beans properties from source to
	   * destination class bean
	   * 
	   * @param sourceList
	   * @param D
	   * @return
	   * @throws InstantiationException
	   * @throws IllegalAccessException
	   */
	  @SuppressWarnings("unchecked")
	  public static <Source, Destination> List<Destination> copyListBeanProperty(
	      Iterable<Source> sourceList, Class destiniation)
	          throws InstantiationException, IllegalAccessException {
	    List<Destination> list = new ArrayList<Destination>();
	    for (Source source : sourceList) {
	      list.add((Destination) copyBeanProperties(source, destiniation));
	    }
	    return list;
	  }

	  /**
	   * Method to copy the the bean properties from source to destination class
	   * bean
	   * 
	   * @param source
	   * @param Destination
	   * @return
	   * @throws InstantiationException
	   * @throws IllegalAccessException
	   */
	  @SuppressWarnings("unchecked")
	  public static <Source, Destination> Destination copyBeanProperties(Source source,
	      Class destinationClass) throws InstantiationException, IllegalAccessException {
	    Destination destination = (Destination) destinationClass.newInstance();
	    BeanUtils.copyProperties(source, destination);
	    return destination;
	  }


}
