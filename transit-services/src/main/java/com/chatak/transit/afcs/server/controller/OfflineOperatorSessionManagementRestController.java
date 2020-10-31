/**
 * @author Girmiti Software
 */
package com.chatak.transit.afcs.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.model.BulkUploadDetails;
import com.chatak.transit.afcs.server.dao.model.Operator;
import com.chatak.transit.afcs.server.dao.model.RouteMaster;
import com.chatak.transit.afcs.server.dao.model.StopProfile;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.HttpErrorCodes;
import com.chatak.transit.afcs.server.exception.PosException;
import com.chatak.transit.afcs.server.pojo.AdminSessionRequest;
import com.chatak.transit.afcs.server.pojo.AdminSessionResponse;
import com.chatak.transit.afcs.server.pojo.FileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.FileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.service.FareManagementService;
import com.chatak.transit.afcs.server.service.OperatorManagementService;
import com.chatak.transit.afcs.server.service.OperatorSessionManagementService;
import com.chatak.transit.afcs.server.service.RouteManagementService;
import com.chatak.transit.afcs.server.service.StopManagementService;
import com.chatak.transit.afcs.server.util.Helper;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.util.Utility;

@RestController
@RequestMapping(value = "/offline/")
public class OfflineOperatorSessionManagementRestController {

	@Autowired
	private OperatorSessionManagementService sessionManagementService;

	@Autowired
	RouteManagementService routeManagementService;

	@Autowired
	OperatorManagementService operatorManagementService;

	@Autowired
	StopManagementService stopManagementService;

	@Autowired
	FareManagementService fareManagementService;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;

	private static Logger logger = LoggerFactory.getLogger(OfflineOperatorSessionManagementRestController.class);

	private static final String JDBC_SQLITE = "jdbc:sqlite:";
	
	private static final String LOG_ERROR = "ERROR :: OfflineOperatorSessionManagementRestController class :: getAllStops method :: exception";
	private static final String ALLOPERATORS_LOG = "ERROR :: OfflineOperatorSessionManagementRestController class :: getAllOperators method :: exception";
	
	@PostMapping(value = "downloadMasters")
	public void downloadMasters(@RequestBody String data, HttpServletResponse httpResponse) throws Exception {

		String info = "Entering:: OfflineOperatorSessionManagementRestController:: downloadMasters method =";
		logger.debug(info , data);
		FileDownloadRequest request = new FileDownloadRequest();
		if (sessionManagementService.validateFileDataLength(data)) {
			Helper.parseFileDownloadRequest(data, request);

			String dir = System.getProperty(ServerConstants.USER_HOME)
					+ System.getProperty(ServerConstants.FILE_SEPERATOR) + ServerConstants.AFCS
					+ System.getProperty(ServerConstants.FILE_SEPERATOR);
			File newFolder = new File(dir + ServerConstants.MASTERS);
			newFolder.mkdirs();
			getFare(request);
			getAllOperators(request);
			getAllRoutes(request);
			getAllStops(request);
			File file = new File(dir + ServerConstants.MASTERS_ZIP);
			String path = dir + ServerConstants.MASTERS_ZIP;
			try {
				zipFolder(newFolder, file);
			} catch (IOException e) {
				logger.error(
						"ERROR :: OfflineOperatorSessionManagementRestController class :: downloadMasters method :: exception",
						e);
			}
			Utility.fetchFile(httpResponse, path);
		} else {
			try {
				httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode());
			} catch (IOException e) {
				logger.error("Exception", e);
			}
		}

	}

	public void zipFolder(File srcFolder, File destZipFile) throws Exception {
		try (FileOutputStream fileWriter = new FileOutputStream(destZipFile);
				ZipOutputStream zip = new ZipOutputStream(fileWriter)) {

			addFolderToZip(srcFolder, srcFolder, zip);
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
				String logInformation = "Zip " + srcFile + "\n to" + name;
				logger.info(logInformation);
				zip.putNextEntry(new ZipEntry(name));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			}
		}
	}

	private void addFolderToZip(File rootPath, File srcFolder, ZipOutputStream zip) throws Exception {
		for (File fileName : srcFolder.listFiles()) {
			addFileToZip(rootPath, fileName, zip);
		}
	}

	private void getFare(FileDownloadRequest request) throws SQLException {
		String dir = System.getProperty(ServerConstants.USER_HOME) + System.getProperty(ServerConstants.FILE_SEPERATOR)
				+ ServerConstants.AFCS + System.getProperty(ServerConstants.FILE_SEPERATOR);

		String url = JDBC_SQLITE + dir + "masters/cmn001.db";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();) {

			String deleteFareMaster = "DROP TABLE IF EXISTS FARE_MASTER;";
			String fareCreateQuery = "CREATE TABLE \"FARE_MASTER\" (\r\n" + "	\"company_code\"	TEXT,\r\n"
					+ "	\"line_number\"	TEXT,\r\n" + "	\"cardtype_code\"	TEXT,\r\n"
					+ "	\"location_code1\"	TEXT,\r\n" + "	\"location_code2\"	TEXT,\r\n"
					+ "	\"fare_amount\"	TEXT\r\n" + ")";

			stmt.execute(deleteFareMaster);
			stmt.execute(fareCreateQuery);
		}catch (SQLException e) {
			logger.error(LOG_ERROR,e);
		}
		
		String insertStopData = "INSERT INTO FARE_MASTER (company_code ,line_number , cardtype_code , location_code1 ,location_code2 ,fare_amount ) VALUES(?, ?, ?,?,?,?);";
		
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement stopPStmt = conn.prepareStatement(insertStopData);) {
			conn.setAutoCommit(false);  
			
			List<BulkUploadDetails> bulkFareList = fareManagementService.getAllBulkFares(Long.valueOf(request.getPtoOperationId()));
			if (StringUtil.isListNotNullNEmpty(bulkFareList)) {
				for (int i = 0; i <= bulkFareList.size() - 1; i++) {
					stopPStmt.setString(1, String.valueOf(bulkFareList.get(i).getPtoId()));
					stopPStmt.setString(2, "FFFF");
					stopPStmt.setString(3, "00");
					stopPStmt.setString(4, bulkFareList.get(i).getStartStopCode());
					stopPStmt.setString(5, bulkFareList.get(i).getEndStopCode());
					stopPStmt.setString(6, String.valueOf(bulkFareList.get(i).getFareAmount()));
					stopPStmt.addBatch();
				}
				stopPStmt.executeBatch(); 
				conn.commit();  
			}
		} catch (SQLException e) {
			logger.error(LOG_ERROR,e);
		}

	}

	private void getAllStops(FileDownloadRequest request) throws SQLException {
		String dir = System.getProperty(ServerConstants.USER_HOME) + System.getProperty(ServerConstants.FILE_SEPERATOR)
				+ ServerConstants.AFCS + System.getProperty(ServerConstants.FILE_SEPERATOR);

		String url = JDBC_SQLITE + dir + "masters/cmn002.db";
		
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();) {

			String deleteStopMaster = "DROP TABLE IF EXISTS STOP_MASTER;";
			String operatorCreateQuery = "CREATE TABLE \"STOP_MASTER\" (\r\n" + "	\"COMPANY_CODE\"	TEXT,\r\n"
					+ "	\"AREA_CODE\"	TEXT,\r\n" + "	\"STATION_CODE\"	TEXT,\r\n" + "	\"STOP_CODE\"	TEXT,\r\n"
					+ "	\"AVAILABLE_REGIST_START_DATE\"	TEXT,\r\n" + "	\"AVAILABLE_REGIST_END_DATE\"	TEXT,\r\n"
					+ "	\"STATION_NAME\"	TEXT,\r\n" + "	\"STOP_NAME\"	TEXT,\r\n"
					+ "	\"STOP_DIVISION\"	TEXT,\r\n" + "	\"LOCATION_X\"	TEXT,\r\n" + "	\"LOCATION_Y\"	TEXT,\r\n"
					+ "	\"GUJARATI_STATION_NAME\"	TEXT\r\n" + ")";

			stmt.execute(deleteStopMaster);
			stmt.execute(operatorCreateQuery);
			
		}catch (SQLException e) {
			logger.error(LOG_ERROR,e);
		}
		
		String insertStopData = "INSERT INTO STOP_MASTER (company_code ,area_code, station_code ,	stop_code ,	available_regist_start_date ,available_regist_end_date ,station_name,stop_name ,stop_division ,location_x  ,location_y ,gujarati_station_name ) VALUES(?, ?, ?,?,?,?,?,?,?,?,?,?);";
		
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement stopPStmt = conn.prepareStatement(insertStopData);) {
			conn.setAutoCommit(false);  
			
			List<StopProfile> stopList = stopManagementService.getAllStopsByPtoId(Long.valueOf(request.getPtoOperationId()));
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
					stopPStmt.addBatch();
				}
				stopPStmt.executeBatch(); 
				conn.commit();
			}
		} catch (SQLException e) {
			logger.error(LOG_ERROR,e);
		}

	}

	private void getAllRoutes(FileDownloadRequest request) throws SQLException {
		String dir = System.getProperty(ServerConstants.USER_HOME) + System.getProperty(ServerConstants.FILE_SEPERATOR)
				+ ServerConstants.AFCS + System.getProperty(ServerConstants.FILE_SEPERATOR);

		String url = JDBC_SQLITE + dir + "masters/cmn003.db";
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();) {

			String deleteExistsedRoutes = "DROP TABLE IF EXISTS STOP_ON_ROUTE_MASTER;";
			String operatorCreateQuery = "CREATE TABLE \"STOP_ON_ROUTE_MASTER\" (\r\n"
					+ "	\"company_code\"	TEXT,\r\n" + "	\"route_code\"	TEXT,\r\n"
					+ "	\"location_type\"	TEXT,\r\n" + "	\"location_cd\"	TEXT,\r\n"
					+ "	\"available_regist_start_date\"	TEXT,\r\n" + "	\"available_regist_end_date\"	TEXT,\r\n"
					+ "	\"sort_cd\"	TEXT\r\n" + ");";

			stmt.execute(deleteExistsedRoutes);
			stmt.execute(operatorCreateQuery);
			
		} catch (SQLException e) {
			logger.error(LOG_ERROR,	e);
		}
		
		String insertRouteData = "INSERT INTO STOP_ON_ROUTE_MASTER (company_code, route_code, location_type,location_cd,available_regist_start_date,available_regist_end_date,sort_cd) VALUES(?, ?, ?,?,?,?,?);";
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement routePStmt = conn.prepareStatement(insertRouteData);) {
			conn.setAutoCommit(false); 
			
			List<RouteMaster> routeList = routeManagementService.getAllRoutes(Long.valueOf(request.getPtoOperationId()));
			if (StringUtil.isListNotNullNEmpty(routeList)) {
				for (RouteMaster route : routeList) {

					List<StopProfile> stopList = stopManagementService.getAllStops(Long.valueOf(request.getPtoOperationId()),
							route.getRouteId());
					for (StopProfile stop : stopList) {
						routePStmt.setString(1, route.getPtoId().toString());
						routePStmt.setString(2, String.valueOf(route.getRouteCode()));
						routePStmt.setString(3, "01");
						routePStmt.setString(4, String.valueOf(stop.getStopId()));
						routePStmt.setString(5, "20180202100000");
						routePStmt.setString(6, "99991231235959");
						routePStmt.setString(7, "1");
						routePStmt.addBatch();
					}
					routePStmt.executeBatch(); 
					conn.commit();
				}
			}
		} catch (SQLException e) {
			logger.error(
					"ERROR :: OfflineOperatorSessionManagementRestController class :: getAllRoutes method :: exception",
					e);
		}
	}

	private void getAllOperators(FileDownloadRequest request) throws SQLException {
		String dir = System.getProperty(ServerConstants.USER_HOME) + System.getProperty(ServerConstants.FILE_SEPERATOR)
				+ ServerConstants.AFCS + System.getProperty(ServerConstants.FILE_SEPERATOR);

		String url = JDBC_SQLITE + dir + "masters/cmn004.db";
		
		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement()) {

			String deleteExistedOperator = "DROP TABLE IF EXISTS USER_DETAILS;";
			String operatorCreateQuery = "CREATE TABLE \"USER_DETAILS\" (\r\n" + "	\"Id\"	TEXT,\r\n"
					+ "	\"name\"	TEXT,\r\n" + "	\"password\"	TEXT\r\n" + ")";

			stmt.execute(deleteExistedOperator);
			stmt.execute(operatorCreateQuery);
			
		} catch (SQLException e) {
			logger.error(ALLOPERATORS_LOG,e);
		}
		
		String insertOperatorData = "INSERT INTO USER_DETAILS (id, name, password) VALUES(?, ?, ?);";
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				PreparedStatement operatorPStmt = conn.prepareStatement(insertOperatorData);) {
            conn.setAutoCommit(false); 
            
            
			List<Operator> operatorList = getOperatorList(request);
			if (StringUtil.isListNotNullNEmpty(operatorList)) {
				for (Operator operator : operatorList) {

					operatorPStmt.setLong(1, operator.getOperatorId());
					operatorPStmt.setString(2, operator.getOperatorUserId());
					operatorPStmt.setString(3, operator.getOperatorPassword());
					operatorPStmt.addBatch();
				}
				operatorPStmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			logger.error(ALLOPERATORS_LOG,e);
		}

	}

	private List<Operator> getOperatorList(FileDownloadRequest request) {
		List<Operator> operatorList = new ArrayList<>();
		
		try {
			operatorList = operatorManagementService.getAllOperator(Long.valueOf(request.getPtoOperationId()));
		} catch (Exception e) {
			logger.error(ALLOPERATORS_LOG, e);
		}
		return operatorList;
	}

	@PostMapping(value = "downloadSoftwares")
	public void downloadsoftWare(@RequestBody String data, HttpServletResponse httpResponse) {
		String info = "Entering:: OfflineOperatorSessionManagementRestController:: downloadMasters method =";
		logger.debug(info , data);
		FileDownloadRequest request = new FileDownloadRequest();
		if (sessionManagementService.validateFileDataLength(data)) {
			Helper.parseFileDownloadRequest(data, request);
			sessionManagementService.softwareDownloadRequest(request, httpResponse);
		} else {
			try {
				httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode());
			} catch (IOException e) {
				logger.error(
						"ERROR :: OfflineOperatorSessionManagementRestController class :: downloadsoftWare method :: exception",
						e);
			}
		}
	}

	@PostMapping(value = "/adminSessionRequest")
	public String saveAdminSession(@RequestBody String data, BindingResult bindingResult,
			HttpServletResponse httpResponse, AdminSessionResponse response)
			throws IOException, PosException {
		String info = "Entering:: adminSessionRequestRestController:: saveRequest method =";
		logger.debug(info , data);
		AdminSessionRequest request = new AdminSessionRequest();
		if (sessionManagementService.validateAdminDataLength(data)) {
			request.parseAdminSessionRequest(data);
			AdminSessionResponse responseAdmin = sessionManagementService.saveAdminSessionRequest(request, httpResponse,
					response);
			String responseLenth = "Admin response length=";
			logger.debug(responseLenth,responseAdmin);
			if (responseAdmin != null) {
				String loggerResponse = responseAdmin.toString();
				logger.debug(loggerResponse);
				return responseAdmin.toString();
			}
		} else {
			response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
			response.setReservedResp("");
			response.setCheckSum("");
			httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode(), response.toString());
			return response.toString();
		}
		return response.toString();
	}

	@PostMapping(value = "/operatorSessionReport")
	public OperatorSessionReportGenerationResponse generateOperatorSessionReport(
			@RequestBody OperatorSessionReportGenerationRequest request,
			OperatorSessionReportGenerationResponse response) {
		return sessionManagementService.generateOperatorSessionReport(request);
	}

	@PostMapping(value = "fileDownloadRequest")
	public String checkVersion(@RequestBody String data, HttpServletResponse httpResponse,
			FileDownloadResponse response) throws IOException {
		String info = "Entering:: fileDownloadRequest:: checkVersion method =";
		logger.debug(info , data);
		FileDownloadRequest request = new FileDownloadRequest();
		if (sessionManagementService.validateFileDataLength(data)) {
			Helper.parseFileDownloadRequest(data, request);
			return sessionManagementService.checkVersion(request, httpResponse, response);
		} else {
			response.setStatusCode(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.INVALID_PACKET_LENGTH.getCustomErrorMsg());
			response.setReservedResp("");
			response.setCheckSum("");
			httpResponse.sendError(HttpErrorCodes.BAD_REQUEST.getHttpErrorCode(), response.toString());
			return response.toString();
		}
	}

}