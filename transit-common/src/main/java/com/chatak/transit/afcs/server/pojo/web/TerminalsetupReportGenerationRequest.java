package com.chatak.transit.afcs.server.pojo.web;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.util.DateUtil;

public class TerminalsetupReportGenerationRequest implements Serializable {

	private static final long serialVersionUID = -3536097436553375077L;

	@NotEmpty(message = "chatak.afcs.service.user.id.required")
	@Pattern(regexp = Constants.EMAIL_REGXP, message = "chatak.afcs.service.user.id.invalid")
	private String userId;
	
	@NotBlank(message = "chatak.afcs.service.ptooperation.id.required")
	@Size(min=12,max = 12, message = "chatak.afcs.service.ptooperation.id.invalid")
	private String ptoOperationId;
	
	private String equimentOemId;
	
	private String equimentModelNumber;
	
	private Timestamp generationDateStart;
	private Timestamp generationDateEnd;
	private int pageIndex;
	
	private Long ptoId;

	public Long getPtoId() {
		return ptoId;
	}

	public void setPtoId(Long ptoId) {
		this.ptoId = ptoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPtoOperationId() {
		return ptoOperationId;
	}

	public void setPtoOperationId(String ptoOperationId) {
		this.ptoOperationId = ptoOperationId;
	}

	public String getEquimentOemId() {
		return equimentOemId;
	}

	public void setEquimentOemId(String equimentOemId) {
		this.equimentOemId = equimentOemId;
	}

	public String getEquimentModelNumber() {
		return equimentModelNumber;
	}

	public void setEquimentModelNumber(String equimentModelNumber) {
		this.equimentModelNumber = equimentModelNumber;
	}

	public Timestamp getGenerationDateStart() {
		return generationDateStart;
	}

	public void setGenerationDateStart(Timestamp generationDateStart) {
		this.generationDateStart = DateUtil.setGenerationDateStartRequest(generationDateStart);
	}

	public Timestamp getGenerationDateEnd() {
		return generationDateEnd;
	}

	public void setGenerationDateEnd(Timestamp generationDateEnd) {
		this.generationDateEnd = DateUtil.setGenerationDateStartRequest(generationDateEnd);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return "TerminalsetupReportGenerationRequest [userId=" + userId + ", ptoOperationId=" + ptoOperationId
				+ ", equimentOemId=" + equimentOemId + ", equimentModelNumber=" + equimentModelNumber
				+ ", generationDateStart=" + generationDateStart + ", generationDateEnd=" + generationDateEnd
				+ ", pageIndex=" + pageIndex + "]";
	}
}
