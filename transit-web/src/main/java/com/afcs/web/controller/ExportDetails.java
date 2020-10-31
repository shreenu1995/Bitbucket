package com.afcs.web.controller;

import java.util.List;
import java.util.Map;

import com.chatak.transit.afcs.server.enums.ExportType; 

public class ExportDetails {
	 private String reportName;
	  private String dateFormatter;
	  private String headerMessageProperty;
	  private ExportType exportType;
	  private List<String> headerList;
	  private List<Object[]> fileData;
	  private Integer excelStartRowNumber;
	  private Map<String, String> map;

	  public String getReportName() {
	    return reportName;
	  }

	  public void setReportName(String reportName) {
	    this.reportName = reportName;
	  }

	  public String getDateFormatter() {
	    return dateFormatter;
	  }

	  public void setDateFormatter(String dateFormatter) {
	    this.dateFormatter = dateFormatter;
	  }

	  public ExportType getExportType() {
	    return exportType;
	  }

	  public void setExportType(ExportType exportType) {
	    this.exportType = exportType;
	  }

	  public List<String> getHeaderList() {
	    return headerList;
	  }

	  public void setHeaderList(List<String> headerList) {
	    this.headerList = headerList;
	  }

	  public List<Object[]> getFileData() {
	    return fileData;
	  }

	  public void setFileData(List<Object[]> fileData) {
	    this.fileData = fileData;
	  }

	  public String getHeaderMessageProperty() {
	    return headerMessageProperty;
	  }

	  public void setHeaderMessageProperty(String headerMessageProperty) {
	    this.headerMessageProperty = headerMessageProperty;
	  }

	  public Integer getExcelStartRowNumber() {
	    return excelStartRowNumber;
	  }

	  public void setExcelStartRowNumber(Integer excelStartRowNumber) {
	    this.excelStartRowNumber = excelStartRowNumber;
	  }


	  public Map<String, String> getMap() {
		 return map;
	  }

	  public void setMap(Map<String, String> map) {
		  this.map = map;
	  }
}
