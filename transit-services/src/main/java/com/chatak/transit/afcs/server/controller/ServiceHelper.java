package com.chatak.transit.afcs.server.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.chatak.transit.afcs.server.pojo.AFCSCommonRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

@PropertySource("classpath:error.properties")
public class ServiceHelper {

	private static final Logger logger = LoggerFactory.getLogger(ServiceHelper.class);
	
	private static final char[] HEX =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static final String TEMP_SECURE_WORD_USER = "@@AFCSTRANSIT@@";
	
	@Autowired
	Environment property;

	protected WebResponse bindingResultErrorDetails(BindingResult bindingResult) {

		WebResponse response = new WebResponse();
		List<ObjectError> objectErrors = bindingResult.getAllErrors();
		List<String> errorCodeList = new ArrayList<>();
		for (ObjectError errors : objectErrors) {
			String objectError = property.getProperty(errors.getDefaultMessage());
			errorCodeList.add(objectError);
		}
		Collections.sort(errorCodeList);
		String errCode = errorCodeList.get(0);

		if (null != errCode) {
			response.setStatusCode(errCode);
			response.setStatusMessage(property.getProperty(errCode));
		}
		logger.info("ServiceHelper clsaa:: bindingResultErrorDetails method :: exit");
		return response;
	}

	// FIX_ME
    protected boolean validateChecksum(AFCSCommonRequest request, String checkSum) {
      try {
        request.setCheckSum(null);
        String object = request.getCurrentDateTime() + request.getRequestID() + request.getTimeZone();
        String temp = TEMP_SECURE_WORD_USER + object + TEMP_SECURE_WORD_USER;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5Binary = messageDigest.digest(temp.getBytes());
        String hexParam = encodeHex(md5Binary).toUpperCase();
        return hexParam.equals(checkSum);
      } catch (NoSuchAlgorithmException e) {
        logger.error("ERROR: PasswordHandler :: encodeUserPassword method", e);
      }
      return false;
    }
  
    private static String encodeHex(byte[] data) {
      int datalength = data.length;
      char[] out = new char[datalength * 2];
      int j = 0;
      for (int i = 0; i < datalength; i++) {
        out[j++] = HEX[(0xf0 & data[i]) >>> 4];
        out[j++] = HEX[0xf & data[i]];
      }
      return new String(out);
    }
	
}
