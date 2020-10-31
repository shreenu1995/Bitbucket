/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.service;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TerminalSetUpResponse;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpResponse;

public interface TerminalSetUpService {
    
	String getTerminalSetupRequest(TerminalSetUpRequest request,
			HttpServletResponse httpResponse) throws PosException, IOException, ParseException;
    
	boolean validateTerminalSetupRequest(TerminalSetUpRequest request) throws PosException, ParseException;
    
	boolean validateSetupDataLength(String data);

	void terminalSetupErrors(TerminalSetUpRequest request, TerminalSetUpResponse response,boolean isValidTimeStamp);
	
	TransitTerminalSetUpResponse getTransitTerminalSetupRequest(TransitTerminalSetUpRequest request,
        HttpServletResponse httpResponse);
  
    boolean validateTransitTerminalSetupRequest(TransitTerminalSetUpRequest request)
        throws PosException, ParseException;
  
    void transitTerminalSetupErrors(TransitTerminalSetUpRequest request,
        TransitTerminalSetUpResponse response, boolean isValidTimeStamp);
}
