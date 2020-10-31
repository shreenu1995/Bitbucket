package com.chatak.transit.afcs.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.AFCSCommonResponse;
import com.chatak.transit.afcs.server.pojo.BatchUploadRequest;
import com.chatak.transit.afcs.server.pojo.HttResponse;
import com.chatak.transit.afcs.server.pojo.TicketTransactionDataResponse;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.TransitFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.TransitLocationUpdateRequest;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpRequest;
import com.chatak.transit.afcs.server.pojo.TransitTerminalSetUpResponse;
import com.chatak.transit.afcs.server.pojo.TransitTicketTransactionDataRequest;
import com.chatak.transit.afcs.server.service.IBatchSummaryService;
import com.chatak.transit.afcs.server.service.DeviceTrackMgmtService;
import com.chatak.transit.afcs.server.service.OperatorSessionManagementService;
import com.chatak.transit.afcs.server.service.TerminalSetUpService;
import com.chatak.transit.afcs.server.service.UploadTicketToAfcsService;

@RestController
@Validated
@RequestMapping(value = "/afcservice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransitServiceRestController extends ServiceHelper {

	private static Logger logger = LoggerFactory.getLogger(TransitServiceRestController.class);

	@Autowired
	private TerminalSetUpService terminalSetupService;

	@Autowired
	private OperatorSessionManagementService sessionManagementService;

	@Autowired
	private IBatchSummaryService batchSummaryService;

	@Autowired
	private DeviceTrackMgmtService deviceTrackMgmtService;

	@Autowired
	private UploadTicketToAfcsService uploadTicketToAfcsService;

	@PostMapping(value = "/terminal/1/0/setup")
	public ResponseEntity<AFCSCommonResponse> setup(HttpServletRequest request,
			@RequestBody @Valid TransitTerminalSetUpRequest terminalSetUpRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse) {
		logger.info("Entering :: TransitServiceRestController :: setup method");
		if (!bindingResult.hasErrors() && validateChecksum(terminalSetUpRequest, terminalSetUpRequest.getCheckSum())) {
			logger.info("TransitServiceRestController :: setup method :: Executing ");
			TransitTerminalSetUpResponse terminalResponse = terminalSetupService
					.getTransitTerminalSetupRequest(terminalSetUpRequest, httpResponse);
			return new ResponseEntity<>(terminalResponse, HttpStatus.OK);
		} else {
			AFCSCommonResponse response = new AFCSCommonResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: setup method :: Invalid Request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/terminal/1/0/checkMasterDataUpdate")
	public ResponseEntity<AFCSCommonResponse> checkMasterDataUpdate(HttpServletRequest request,
			@RequestBody @Valid TransitFileDownloadRequest fileDownloadRequest, BindingResult bindingResult,
			HttpServletResponse httpResponse, TransitFileDownloadResponse fileDownloadResponse) {
		logger.info("Entering :: TransitServiceRestController :: checkMasterDataUpdate method");
		if (!bindingResult.hasErrors() && validateChecksum(fileDownloadRequest, fileDownloadRequest.getCheckSum())) {
			logger.info("TransitServiceRestController :: checkMasterDataUpdate method :: Executing ");
			fileDownloadResponse = sessionManagementService.checkTransitVersion(fileDownloadRequest, httpResponse,
					fileDownloadResponse);
			return new ResponseEntity<>(fileDownloadResponse, HttpStatus.OK);
		} else {
			AFCSCommonResponse response = new AFCSCommonResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: checkMasterDataUpdate method :: Invalid Request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/terminal/1/0/downloadMasterData")
	public ResponseEntity<AFCSCommonResponse> downloadMasterData(HttpServletRequest httpRequest,
			@RequestBody @Valid TransitFileDownloadRequest request, HttpServletResponse httpResponse,
			BindingResult bindingResult) {
		logger.info("Entering :: TransitServiceRestController :: downloadMasterData method");
		if (!bindingResult.hasErrors() && validateChecksum(request, request.getCheckSum())) {
			logger.info("TransitServiceRestController :: downloadMasterData method :: Executing ");
			TransitFileDownloadResponse fileDownloadResponse = sessionManagementService.downloadMasterData(request,
					httpResponse, httpRequest);
			return new ResponseEntity<>(fileDownloadResponse, HttpStatus.OK);
		} else {
			AFCSCommonResponse response = new AFCSCommonResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: downloadMasterData method :: Invalid Request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/terminal/1/0/locationUpdate")
	public ResponseEntity<AFCSCommonResponse> locationUpdate(HttpServletRequest httpRequest,
			@RequestBody @Valid TransitLocationUpdateRequest request, HttpServletResponse httpResponse,
			BindingResult bindingResult) {
		logger.info("Entering :: TransitServiceRestController :: locationUpdate method");
		if (!bindingResult.hasErrors() && validateChecksum(request, request.getCheckSum())) {
			logger.info("TransitServiceRestController :: locationUpdate method :: Executing ");
			AFCSCommonResponse afcsCommonResponse = deviceTrackMgmtService.updateLocation(request);
			return new ResponseEntity<>(afcsCommonResponse, HttpStatus.OK);
		} else {
			AFCSCommonResponse response = new AFCSCommonResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: locationUpdate method :: Invalid Request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/transaction/1/0/upload")
	public ResponseEntity<HttResponse> uploadTicket(@RequestBody @Valid TransitTicketTransactionDataRequest request,
			BindingResult bindingResult, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		logger.info("Entering :: TransitServiceRestController :: uploadTicket method");
		if (!bindingResult.hasErrors() && validateChecksum(request, request.getCheckSum())) {
			logger.info("TransitServiceRestController :: uploadTicket method :: Executing ");

			TicketTransactionDataResponse ticketTxnResponse = uploadTicketToAfcsService.saveUploadTicket(request);
			return new ResponseEntity<>(ticketTxnResponse, HttpStatus.OK);
		} else {
			HttResponse response = new HttResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: uploadTicket method :: Invalid Request");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/transaction/1/0/batchUpload")
	public ResponseEntity<AFCSCommonResponse> batchUpload(@RequestBody @Valid BatchUploadRequest request,
			BindingResult bindingResult) {
		AFCSCommonResponse response = null;
		logger.info("Entering :: TransitServiceRestController :: batchUpload method :: Entered");
		if (!bindingResult.hasErrors()) {
			logger.info("TransitServiceRestController :: batchUpload method :: Executing");
			response = batchSummaryService.saveBatchSummaryDetails(request);
			logger.info("TransitServiceRestController :: batchUpload method :: Success");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new AFCSCommonResponse();
			response.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
			logger.info("TransitServiceRestController :: batchUpload method :: Failure");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
