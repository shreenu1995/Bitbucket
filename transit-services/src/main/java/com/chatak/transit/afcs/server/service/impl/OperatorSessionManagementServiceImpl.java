/**
 * @author Girmiti Software
 */

package com.chatak.transit.afcs.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.OperatorSessionManagementDao;
import com.chatak.transit.afcs.server.dao.model.AdminSessionManagement;
import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.DeviceModelRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.AdminSessionResponse;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.service.FareManagementService;
import com.chatak.transit.afcs.server.service.OperatorManagementService;
import com.chatak.transit.afcs.server.service.OperatorSessionManagementService;
import com.chatak.transit.afcs.server.service.RouteManagementService;
import com.chatak.transit.afcs.server.service.StopManagementService;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.util.Utility;
import com.chatak.transit.afcs.server.util.Version;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OperatorSessionManagementServiceImpl implements OperatorSessionManagementService {

	Logger logger = LoggerFactory.getLogger(OperatorSessionManagementServiceImpl.class);

	@Autowired
	CustomErrorResolution dataValidation;

	@Autowired
	OperatorSessionManagementDao operatorSessionManagementDao;
	
	@Autowired
    OperatorManagementService operatorManagementService;
	
	@Autowired
    FareManagementService fareManagementService;
	
	@Autowired
    RouteManagementService routeManagementService;
	
	@Autowired
    StopManagementService stopManagementService;
	
	@Autowired
	DeviceModelRepository deviceRepository;
	
	@Autowired
	PtoMasterRepository ptoRespository;

	@PersistenceContext
	private EntityManager em;

	private static final String EXCEPTION = "Exception";

	private static final String CHECK_DATA = "Check data=";
	
	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	private static final String DATA_VALIDATION_ERROR = "Data Validation Failed.";
	

	@Override
	public AdminSessionResponse saveAdminSessionRequest(AdminSessionRequest request, HttpServletResponse httpResponse,
			AdminSessionResponse response) throws PosException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String content = null;
		try {
			String validation = "Validation=";
			logger.info(validation, operatorSessionManagementDao.validateAdminSessionRequest(request));
			if (operatorSessionManagementDao.validateAdminSessionRequest(request)) {
				AdminSessionManagement adminSessionManagement = new AdminSessionManagement();
				adminSessionManagement.setUserId(request.getUserId());
				adminSessionManagement.setPtoId(request.getPtoId());
				adminSessionManagement.setEmpId(request.getEmpId());
				adminSessionManagement.setDeviceId(request.getDeviceId());
				adminSessionManagement.setTransactionId(request.getTransactionId());
				adminSessionManagement.setGenerateDateAndTime(request.getDateTime());
				adminSessionManagement.setProcessDateAndtime(Timestamp.from(Instant.now()));
				if (operatorSessionManagementDao.saveAdminSessionRequest(adminSessionManagement)) {
					response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
					response.setReservedResp("");
					response.setCheckSum("");
					content = mapper.writeValueAsString(response.toString());
					httpResponse.setContentType("application/json");
					httpResponse.setContentLength(content.length());
					httpResponse.setCharacterEncoding(ServerConstants.CHAR_ENCODING_CONS);
					return response;
				} else {
					response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
					response.setReservedResp("");
					response.setCheckSum("");
					httpResponse.sendError(HttpErrorCodes.INTERNAL_SERVER_ERROR.getHttpErrorCode(),
							response.toString());
					return response;
				}
			} else {
				adminSessionErrors(request, response);
				response.setReservedResp("");
				response.setCheckSum("");
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
				logger.debug(DATA_VALIDATION_ERROR);
				return response;
			}

		} catch (Exception e) {
			logger.error(EXCEPTION, e);

		}
		return response;
	}

	@Override
	public boolean validateAdminDataLength(String data) {
		logger.info("Inside validateAdminDataLength() function ");
		String dataLenth = "AdminSession Data Length is: ";
		logger.info(dataLenth , (data.length() == ServerConstants.ADMIN_SESSION_DATA_LENGTH));
		return (data.length() == ServerConstants.ADMIN_SESSION_DATA_LENGTH);
	}

	@Override
	public void adminSessionErrors(AdminSessionRequest request, AdminSessionResponse response) {
		if (!dataValidation.ptoIdValidation(request.getPtoId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		} else if (!dataValidation.isValidUser(request.getUserId())) {
			response.setStatusCode(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.USER_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public String checkVersion(FileDownloadRequest request, HttpServletResponse httpResponse,
			FileDownloadResponse response) {

		if (operatorSessionManagementDao.validateFileDownloadRequest(request, response)) {
			String version = request.getVersion();
			logger.info(CHECK_DATA, version);
			Version a = new Version(ServerConstants.VERSION);
			Version b = new Version(version.substring(6, 14));
			int check = a.compareTo(b);
			if (check == 0) {
				response.setStatusCode(CustomErrorCodes.VERSION_UPDATE_NOT_REQUIRED.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.VERSION_UPDATE_NOT_REQUIRED.getCustomErrorMsg());
				response.setReservedResp("");
			} else if (check < 0) {
				response.setStatusCode(CustomErrorCodes.VERSION_WRONG.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.VERSION_WRONG.getCustomErrorMsg());
				response.setReservedResp("");
			} else if (check > 0) {
				response.setStatusCode(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorMsg());
				response.setReservedResp(ServerConstants.VERSION);
			}
			response.setCheckSum("");
			return response.toString();

		} else {
			checkFileDownloadErrors(request, response);
			response.setReservedResp("");
			response.setCheckSum("");
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
			logger.debug(DATA_VALIDATION_ERROR);
			return response.toString();
		}

	}

	@Override
	public boolean validateFileDataLength(String data) {
		logger.info("Inside validateFileDataLength() function ");
		String dataLenth = "AdminSession Data Length is: ";
		logger.info(dataLenth , (data.length() == ServerConstants.FILE_DATA_LENGTH));
		return (data.length() == ServerConstants.FILE_DATA_LENGTH);
	}

	@Override
	public void checkFileDownloadErrors(FileDownloadRequest request, FileDownloadResponse response) {
		if (!dataValidation.ptoIdValidation(request.getPtoMasterId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		}
	}

	@Override
	public void softwareDownloadRequest(FileDownloadRequest request, HttpServletResponse httpResponse) {
		FileDownloadResponse response = new FileDownloadResponse();
		if (operatorSessionManagementDao.validateFileDownloadRequest(request, response)) {
			fetchSoftwareFile(request, httpResponse, response);
		} else {
			checkFileDownloadErrors(request, response);
			response.setReservedResp("");
			response.setCheckSum("");
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
			logger.debug(DATA_VALIDATION_ERROR);
		}
	}

	private void fetchSoftwareFile(FileDownloadRequest request, HttpServletResponse httpResponse,
			FileDownloadResponse response) {
		String version = request.getVersion();
		logger.info(CHECK_DATA ,ServerConstants.VERSION);
		Version a = new Version(ServerConstants.VERSION);
		Version b = new Version(version.substring(6, 14));
		int check = a.compareTo(b);
		if (check == 0) {
			try {
				Utility.fetchFile(httpResponse, "/home/rupesh/Downloads/app-debug.zip");
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
		} else if (check<0) {
			response.setStatusCode(CustomErrorCodes.VERSION_WRONG.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.VERSION_WRONG.getCustomErrorMsg());
			response.setReservedResp("");
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
		} else if (check>0) {
			response.setStatusCode(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorMsg());
			response.setReservedResp(ServerConstants.VERSION);
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
		}
		response.setCheckSum("");
	}

	@Override
	public OperatorSessionReportGenerationResponse generateOperatorSessionReport(
			OperatorSessionReportGenerationRequest request) {

		OperatorSessionReportGenerationResponse response = new OperatorSessionReportGenerationResponse();
		if (!StringUtil.isNull(request)) {
			response = operatorSessionManagementDao.generateOperatorSessionReport(request);

			if (!StringUtil.isNull(response)) {
				response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
				return response;
			} else {
				response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			}

		}
		return response;
	}

	@Override
	public TransitFileDownloadResponse checkTransitVersion(TransitFileDownloadRequest request,
			HttpServletResponse httpResponse, TransitFileDownloadResponse response) {
		if (operatorSessionManagementDao.validateTransitFileDownloadRequest(request, response)) {
			if (!StringUtil.isNull(request.getDeviceId())
					&& deviceRepository.existsByDeviceId(Long.parseLong(request.getDeviceId()))) {
				if (!StringUtil.isNull(request.getPtoId())
						&& ptoRespository.existsByPtoMasterId(request.getPtoMasterId())) {
					String version = request.getVersion();
					logger.info(CHECK_DATA, version);
					Version a = new Version(ServerConstants.VERSION);
					Version b = new Version(version);
					int check = a.compareTo(b);
					validateVersion(response, check);
				} else {
					response.setStatusCode(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorCode());
					response.setStatusMessage(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorMsg());
				}
			} else {
				response.setStatusCode(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorCode());
				response.setStatusMessage(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorMsg());
			}
			response.setRequestID(request.getRequestID());
			return response;

		} else {
			checkFileDownloadErrors(request, response);
			try {
				httpResponse.sendError(HttpErrorCodes.NOT_FOUND.getHttpErrorCode(), response.toString());
			} catch (IOException e) {
				logger.error(EXCEPTION, e);
			}
			logger.debug(DATA_VALIDATION_ERROR);
			return response;
		}

	}

	private void validateVersion(TransitFileDownloadResponse response, int check) {
		if (check == 0) {
			response.setStatusCode(CustomErrorCodes.VERSION_UPDATE_NOT_REQUIRED.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.VERSION_UPDATE_NOT_REQUIRED.getCustomErrorMsg());
		} else if (check < 0) {
			response.setStatusCode(CustomErrorCodes.VERSION_WRONG.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.VERSION_WRONG.getCustomErrorMsg());
		} else if (check > 0) {
			response.setStatusCode(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.VERSION_UPDATE_REQUIRED.getCustomErrorMsg());
			response.setVersion(ServerConstants.VERSION);
		}
	}

    private void checkFileDownloadErrors(TransitFileDownloadRequest request, TransitFileDownloadResponse response) {
    	if (!dataValidation.ptoIdValidation(request.getPtoMasterId())) {
			response.setStatusCode(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.PTO_OPERATION_NOT_EXIST.getCustomErrorMsg());
		}
		
	}

	@Override
    public TransitFileDownloadResponse downloadMasterData(TransitFileDownloadRequest request,
        HttpServletResponse httpResponse,HttpServletRequest httpRequest) {
      TransitFileDownloadResponse response = new TransitFileDownloadResponse();
      if(!StringUtil.isNull(request.getDeviceId())&&deviceRepository.existsByDeviceId(Long.parseLong(request.getDeviceId()))){
        if(!StringUtil.isNull(request.getPtoId())&&ptoRespository.existsByPtoMasterId(request.getPtoMasterId())){
          String dir = System.getProperty(ServerConstants.USER_HOME)
              + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.TOMCAT
              + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.WEBAPPS
              + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.ROOT
          + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
          + System.getProperty(ServerConstants.FILE_SEPERATOR);
      File newFolder = new File(dir + ServerConstants.MASTERS);
      newFolder.mkdirs();
      getFare(request);
      try {
        getAllOperators(request);
        getAllRoutes(request);
        getAllStops(request);
        File file = new File(dir + ServerConstants.MASTERS_ZIP);
        zipFolder(newFolder, file);
        response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
        response.setDownloadPath(httpRequest.getProtocol().split("/")[0].toLowerCase() + ":"
            + System.getProperty(ServerConstants.FILE_SEPERATOR)
            + System.getProperty(ServerConstants.FILE_SEPERATOR) + httpRequest.getRemoteAddr()
            + ":8080" + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
            + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.MASTERS_ZIP);
      } catch (Exception e) {
        logger.error(
            "ERROR :: OperatorSessionManagementServiceImpl class :: downloadMasterData method :: exception",
            e);
        response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
      }
        }else{
          response.setStatusCode(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorCode());
          response.setStatusMessage(CustomErrorCodes.PTO_NOT_EXIST.getCustomErrorMsg());
        }
      }else{
        response.setStatusCode(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorCode());
        response.setStatusMessage(CustomErrorCodes.DEVICE_NOT_ACTIVE.getCustomErrorMsg());
      }
      response.setRequestID(request.getRequestID());
      return response;
    }
  
    private void getFare(TransitFileDownloadRequest request) {
      String dir = System.getProperty(ServerConstants.USER_HOME)
          + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
          + System.getProperty(ServerConstants.FILE_SEPERATOR);
  
      String url = JDBC_SQLITE + dir + "masters/cmn001.db";
      String insertStopData =
          "INSERT INTO FARE_MASTER (company_code ,line_number , cardtype_code , location_code1 ,location_code2 ,fare_amount ) VALUES(?, ?, ?,?,?,?);";
  
      try (Connection conn = DriverManager.getConnection(url);
          Statement stmt = conn.createStatement();
          PreparedStatement stopPStmt = conn.prepareStatement(insertStopData);) {
  
        String deleteFareMaster = "DROP TABLE IF EXISTS FARE_MASTER;";
        String fareCreateQuery = "CREATE TABLE \"FARE_MASTER\" (\r\n"
            + "   \"company_code\"    TEXT,\r\n" + " \"line_number\" TEXT,\r\n"
            + "  \"cardtype_code\"   TEXT,\r\n" + " \"location_code1\"  TEXT,\r\n"
            + "  \"location_code2\"  TEXT,\r\n" + " \"fare_amount\" TEXT\r\n" + ")";
  
        stmt.execute(deleteFareMaster);
        stmt.execute(fareCreateQuery);
        List<BulkUploadDetails> bulkFareList =
            fareManagementService.getAllBulkFares(Long.valueOf(request.getPtoId()));
        if (StringUtil.isListNotNullNEmpty(bulkFareList)) {
          for (int i = 0; i <= bulkFareList.size() - 1; i++) {
            stopPStmt.setString(1, String.valueOf(bulkFareList.get(i).getPtoId()));
            stopPStmt.setString(2, "FFFF");
            stopPStmt.setString(3, "00");
            stopPStmt.setString(4, bulkFareList.get(i).getStartStopCode());
            stopPStmt.setString(5, bulkFareList.get(i).getEndStopCode());
            stopPStmt.setString(6, String.valueOf(bulkFareList.get(i).getFareAmount()));
            stopPStmt.executeUpdate();
          }
        }
      } catch (SQLException e) {
        logger.error(
            "ERROR :: OperatorSessionManagementServiceImpl class :: getFare method :: exception",
            e);
      }
  
    }
  
    private void getAllOperators(TransitFileDownloadRequest request) throws SQLException {
      String dir = System.getProperty(ServerConstants.USER_HOME)
          + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
          + System.getProperty(ServerConstants.FILE_SEPERATOR);
  
      String url = JDBC_SQLITE + dir + "masters/cmn004.db";
      String insertOperatorData = "INSERT INTO USER_DETAILS (id, name, password) VALUES(?, ?, ?);";
      try (Connection conn = DriverManager.getConnection(url);
          Statement stmt = conn.createStatement();
          PreparedStatement operatorPStmt = conn.prepareStatement(insertOperatorData);) {
  
        String deleteExistedOperator = "DROP TABLE IF EXISTS USER_DETAILS;";
        String operatorCreateQuery = "CREATE TABLE \"USER_DETAILS\" (\r\n" + "  \"Id\"  TEXT,\r\n"
            + " \"name\"    TEXT,\r\n" + "  \"password\"    TEXT\r\n" + ")";
  
        stmt.execute(deleteExistedOperator);
        stmt.execute(operatorCreateQuery);
        List<Operator> operatorList = getOperatorList(request);
        if (StringUtil.isListNotNullNEmpty(operatorList)) {
          for (Operator operator : operatorList) {
  
            operatorPStmt.setLong(1, operator.getOperatorId());
            operatorPStmt.setString(2, operator.getOperatorUserId());
            operatorPStmt.setString(3, operator.getOperatorPassword());
            operatorPStmt.executeUpdate();
          }
        }
      } catch (SQLException e) {
        logger.error(
            "ERROR :: OperatorSessionManagementServiceImpl class :: getAllOperators method :: exception",
            e);
      }
  
    }

	private List<Operator> getOperatorList(TransitFileDownloadRequest request) {
		List<Operator> operatorList = new ArrayList<>();
		
		PtoMaster ptoMaster = ptoRespository.findByPtoMasterId(request.getPtoMasterId());
					
		try {
			operatorList = operatorManagementService.getAllOperator(ptoMaster.getPtoMasterId());
		} catch (Exception e) {
			logger.error("ERROR :: OperatorSessionManagementServiceImpl class :: getOperatorList method :: exception",
					e);
		}
		return operatorList;
	}
  
    private void getAllRoutes(TransitFileDownloadRequest request) throws SQLException {
      String dir = System.getProperty(ServerConstants.USER_HOME)
          + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
          + System.getProperty(ServerConstants.FILE_SEPERATOR);
  
      String url = JDBC_SQLITE + dir + "masters/cmn003.db";
      String insertRouteData =
          "INSERT INTO STOP_ON_ROUTE_MASTER (company_code, route_code, location_type,location_cd,available_regist_start_date,available_regist_end_date,sort_cd) VALUES(?, ?, ?,?,?,?,?);";
  
      try (Connection conn = DriverManager.getConnection(url);
          Statement stmt = conn.createStatement();
          PreparedStatement routePStmt = conn.prepareStatement(insertRouteData);) {
  
        String deleteExistsedRoutes = "DROP TABLE IF EXISTS STOP_ON_ROUTE_MASTER;";
        String operatorCreateQuery =
            "CREATE TABLE \"STOP_ON_ROUTE_MASTER\" (\r\n" + " \"company_code\"    TEXT,\r\n"
                + "  \"route_code\"  TEXT,\r\n" + " \"location_type\"   TEXT,\r\n"
                + "  \"location_cd\" TEXT,\r\n" + " \"available_regist_start_date\" TEXT,\r\n"
                + "  \"available_regist_end_date\"   TEXT,\r\n" + " \"sort_cd\" TEXT\r\n" + ");";
  
        stmt.execute(deleteExistsedRoutes);
        stmt.execute(operatorCreateQuery);
        List<RouteMaster> routeList = routeManagementService.getAllRoutes(Long.valueOf(request.getPtoId()));
      	PtoMaster ptoMaster = ptoRespository.findByPtoMasterId(request.getPtoMasterId());
        if (StringUtil.isListNotNullNEmpty(routeList)) {
          for (RouteMaster route : routeList) {
  
            List<StopProfile> stopList =
                stopManagementService.getAllStops(ptoMaster.getPtoMasterId(), route.getRouteId());
            for (StopProfile stop : stopList) {
              routePStmt.setString(1, route.getPtoId().toString());
              routePStmt.setString(2, String.valueOf(route.getRouteCode()));
              routePStmt.setString(3, "01");
              routePStmt.setString(4, String.valueOf(stop.getStopId()));
              routePStmt.setString(5, "20180202100000");
              routePStmt.setString(6, "99991231235959");
              routePStmt.setString(7, "1");
              routePStmt.executeUpdate();
            }
          }
        }
      } catch (SQLException e) {
        logger.error(
            "ERROR :: OperatorSessionManagementServiceImpl class :: getAllRoutes method :: exception",
            e);
      }
    }
  
    private void getAllStops(TransitFileDownloadRequest request) throws SQLException {
      String dir = System.getProperty(ServerConstants.USER_HOME)
          + System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
          + System.getProperty(ServerConstants.FILE_SEPERATOR);
  
      String url = JDBC_SQLITE + dir + "masters/cmn002.db";
      String insertStopData =
          "INSERT INTO STOP_MASTER (company_code ,area_code, station_code ,   stop_code , available_regist_start_date ,available_regist_end_date ,station_name,stop_name ,stop_division ,location_x  ,location_y ,gujarati_station_name ) VALUES(?, ?, ?,?,?,?,?,?,?,?,?,?);";
      try (Connection conn = DriverManager.getConnection(url);
          Statement stmt = conn.createStatement();
          PreparedStatement stopPStmt = conn.prepareStatement(insertStopData);) {
  
        String deleteStopMaster = "DROP TABLE IF EXISTS STOP_MASTER;";
        String operatorCreateQuery =
            "CREATE TABLE \"STOP_MASTER\" (\r\n" + "   \"COMPANY_CODE\"    TEXT,\r\n"
                + " \"AREA_CODE\"   TEXT,\r\n" + "  \"STATION_CODE\"    TEXT,\r\n"
                + "  \"STOP_CODE\"   TEXT,\r\n" + " \"AVAILABLE_REGIST_START_DATE\" TEXT,\r\n"
                + "  \"AVAILABLE_REGIST_END_DATE\"   TEXT,\r\n" + " \"STATION_NAME\"    TEXT,\r\n"
                + "  \"STOP_NAME\"   TEXT,\r\n" + " \"STOP_DIVISION\"   TEXT,\r\n"
                + "  \"LOCATION_X\"  TEXT,\r\n" + "  \"LOCATION_Y\"  TEXT,\r\n"
                + " \"GUJARATI_STATION_NAME\"   TEXT\r\n" + ")";
  
        stmt.execute(deleteStopMaster);
        stmt.execute(operatorCreateQuery);
        
       
      	PtoMaster ptoMaster = ptoRespository.findByPtoMasterId(request.getPtoMasterId());
        
        List<StopProfile> stopList = stopManagementService.getAllStopsByPtoId(ptoMaster.getPtoMasterId());
        if (StringUtil.isListNotNullNEmpty(stopList)) {
          for (StopProfile stop : stopList) {
            stopPStmt.setString(1, stop.getPtoId().toString());
            stopPStmt.setString(2, "12");
            stopPStmt.setString(3, String.valueOf(stop.getStopId()));
            stopPStmt.setString(4, String.valueOf(stop.getStopId()));
            stopPStmt.setString(5, "20170800000000");
            stopPStmt.setString(6, "99991200000000");
            stopPStmt.setString(7, stop.getStopName());
            stopPStmt.setString(8, stop.getStopName());
            stopPStmt.setString(9, "00");
            stopPStmt.setString(10, "");
            stopPStmt.setString(11, "");
            stopPStmt.setString(12, "");
            stopPStmt.executeUpdate();
          }
        }
      } catch (SQLException e) {
        logger.error(
            "ERROR :: OperatorSessionManagementServiceImpl class :: getAllStops method :: exception",
            e);
      }
  
    }
  
    public void zipFolder(File srcFolder, File destZipFile) throws Exception {
      try (FileOutputStream fileWriter = new FileOutputStream(destZipFile);
          ZipOutputStream zip = new ZipOutputStream(fileWriter)) {
  
        addFolderToZip(srcFolder, srcFolder, zip);
      }
    }
  
    private void addFolderToZip(File rootPath, File srcFolder, ZipOutputStream zip) throws Exception {
      for (File fileName : srcFolder.listFiles()) {
        addFileToZip(rootPath, fileName, zip);
      }
    }
  
    private void addFileToZip(File rootPath, File srcFile, ZipOutputStream zip) throws Exception {
  
      if (srcFile.isDirectory()) {
        addFolderToZip(rootPath, srcFile, zip);
      } else {
        byte[] buf = new byte[1024];
        int len;
        try (FileInputStream in = new FileInputStream(srcFile)) {
          String name = srcFile.getPath();
          name = name.replace(rootPath.getPath(), "");
          String logInformation = "Zip " + srcFile + "\n to " + name;
          logger.info(logInformation);
          zip.putNextEntry(new ZipEntry(name));
          while ((len = in.read(buf)) > 0) {
            zip.write(buf, 0, len);
          }
        }
      }
    }

}
