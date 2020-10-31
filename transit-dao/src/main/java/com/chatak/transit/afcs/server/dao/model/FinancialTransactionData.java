package com.chatak.transit.afcs.server.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "financial_txn_data")
public class FinancialTransactionData implements Serializable {

	private static final long serialVersionUID = -1733652069705297001L;

	@Id
	@SequenceGenerator(name = "seq_financial_txn_table", sequenceName = "seq_financial_txn_table")
	@GeneratedValue(generator = "seq_financial_txn_table")

	@Column(name = "id")
	private long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "pto_operation_id")
	private String ptoOperationId;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "financial_txn_type")
	private String financialTxnType;

	@Column(name = "date_time")
	private String dateTime;

	@Column(name = "count_0")
	private int count0;

	@Column(name = "amount_0")
	private double amount0;

	@Column(name = "count_1")
	private int count1;

	@Column(name = "amount_1")
	private double amount1;

	@Column(name = "count_2")
	private int count2;

	@Column(name = "amount_2")
	private double amount2;

	@Column(name = "count_3")
	private int count3;

	@Column(name = "amount_3")
	private double amount3;

	@Column(name = "count_4")
	private int count4;

	@Column(name = "amount_4")
	private double amount4;

	@Column(name = "count_5")
	private int count5;

	@Column(name = "amount_5")
	private double amount5;

	@Column(name = "count_6")
	private int count6;

	@Column(name = "amount_6")
	private double amount6;

	@Column(name = "count_7")
	private int count7;

	@Column(name = "amount_7")
	private double amount7;

	@Column(name = "count_8")
	private int count8;

	@Column(name = "amount_8")
	private double amount8;

	@Column(name = "count_9")
	private int count9;

	@Column(name = "amount_9")
	private double amount9;

	@Column(name = "count_10")
	private int count10;

	@Column(name = "amount_10")
	private double amount10;

	@Column(name = "count_11")
	private int count11;

	@Column(name = "amount_11")
	private double amount11;

	@Column(name = "count_12")
	private int count12;

	@Column(name = "amount_12")
	private double amount12;

	@Column(name = "count_13")
	private int count13;

	@Column(name = "amount_13")
	private double amount13;

	@Column(name = "count_14")
	private int count14;

	@Column(name = "amount_14")
	private double amount14;

	@Column(name = "count_15")
	private int count15;

	@Column(name = "amount_15")
	private double amount15;

	@Column(name = "count_16")
	private int count16;

	@Column(name = "amount_16")
	private double amount16;

	@Column(name = "count_17")
	private int count17;

	@Column(name = "amount_17")
	private double amount17;

	@Column(name = "count_18")
	private int count18;

	@Column(name = "amount_18")
	private double amount18;

	@Column(name = "count_19")
	private int count19;

	@Column(name = "amount_19")
	private double amount19;

	@Column(name = "count_20")
	private int count20;

	@Column(name = "amount_20")
	private double amount20;

	@Column(name = "count_21")
	private int count21;

	@Column(name = "amount_21")
	private double amount21;

	@Column(name = "count_22")
	private int count22;

	@Column(name = "amount_22")
	private double amount22;

	@Column(name = "count_23")
	private int count23;

	@Column(name = "amount_23")
	private double amount23;

	@Column(name = "count_24")
	private int count24;

	@Column(name = "amount_24")
	private double amount24;

	@Column(name = "count_25")
	private int count25;

	@Column(name = "amount_25")
	private double amount25;

	@Column(name = "count_26")
	private int count26;

	@Column(name = "amount_26")
	private double amount26;

	@Column(name = "count_27")
	private int count27;

	@Column(name = "amount_27")
	private double amount27;

	@Column(name = "count_28")
	private int count28;

	@Column(name = "amount_28")
	private double amount28;

	@Column(name = "count_29")
	private int count29;

	@Column(name = "amount_29")
	private double amount29;

	@Column(name = "count_30")
	private int count30;

	@Column(name = "amount_30")
	private double amount30;

	@Column(name = "count_31")
	private int count31;

	@Column(name = "amount_31")
	private double amount31;

	@Column(name = "count_32")
	private int count32;

	@Column(name = "amount_32")
	private double amount32;

	@Column(name = "count_33")
	private int count33;

	@Column(name = "amount_33")
	private double amount33;

	@Column(name = "count_34")
	private int count34;

	@Column(name = "amount_34")
	private double amount34;

	@Column(name = "count_35")
	private int count35;

	@Column(name = "amount_35")
	private double amount35;

	@Column(name = "count_36")
	private int count36;

	@Column(name = "amount_36")
	private double amount36;

	@Column(name = "count_37")
	private int count37;

	@Column(name = "amount_37")
	private double amount37;

	@Column(name = "count_38")
	private int count38;

	@Column(name = "amount_38")
	private double amount38;

	@Column(name = "count_39")
	private int count39;

	@Column(name = "amount_39")
	private double amount39;

	@Column(name = "count_40")
	private int count40;

	@Column(name = "amount_40")
	private double amount40;

	@Column(name = "count_41")
	private int count41;

	@Column(name = "amount_41")
	private double amount41;

	@Column(name = "count_42")
	private int count42;

	@Column(name = "amount_42")
	private double amount42;

	@Column(name = "count_43")
	private int count43;

	@Column(name = "amount_43")
	private double amount43;

	@Column(name = "count_44")
	private int count44;

	@Column(name = "amount_44")
	private double amount44;

	@Column(name = "count_45")
	private int count45;

	@Column(name = "amount_45")
	private double amount45;

	@Column(name = "count_46")
	private int count46;

	@Column(name = "amount_46")
	private double amount46;

	@Column(name = "count_47")
	private int count47;

	@Column(name = "amount_47")
	private double amount47;

	@Column(name = "count_48")
	private int count48;

	@Column(name = "amount_48")
	private double amount48;

	@Column(name = "count_49")
	private int count49;

	@Column(name = "amount_49")
	private double amount49;

	@Column(name = "shift_code")
	private String shiftCode;

	@Column(name = "shift_batch_no")
	private int shiftBatchNo;

	@Column(name = "trip_no")
	private String tripNo;

	@Column(name = "payment_host_batch_no")
	private String paymentHostBatchNo;

	@Column(name = "payment_host_tid")
	private String paymentHostTid;

	@Column(name = "software_version")
	private String softwareVersion;

	@Column(name = "master_version")
	private String masterVersion;

	@Column(name = "device_serial_no")
	private String deviceSerialNo;

	@Column(name = "device_model_no")
	private String deviceModelNo;

	@Column(name = "device_component_version")
	private String deviceComponentVersion;

	@Column(name = "created_date_time")
	private Timestamp createdDateTime;

	@Column(name = "updated_date_time")
	private Timestamp updatedDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getFinancialTxnType() {
		return financialTxnType;
	}

	public void setFinancialTxnType(String financialTxnType) {
		this.financialTxnType = financialTxnType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getCount0() {
		return count0;
	}

	public void setCount0(int count0) {
		this.count0 = count0;
	}

	public double getAmount0() {
		return amount0;
	}

	public void setAmount0(Double amount) {
		this.amount0 = amount;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public double getAmount1() {
		return amount1;
	}

	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public double getAmount2() {
		return amount2;
	}

	public void setAmount2(double amount2) {
		this.amount2 = amount2;
	}

	public int getCount3() {
		return count3;
	}

	public void setCount3(int count3) {
		this.count3 = count3;
	}

	public double getAmount3() {
		return amount3;
	}

	public void setAmount3(double amount3) {
		this.amount3 = amount3;
	}

	public int getCount4() {
		return count4;
	}

	public void setCount4(int count4) {
		this.count4 = count4;
	}

	public double getAmount4() {
		return amount4;
	}

	public void setAmount4(double amount4) {
		this.amount4 = amount4;
	}

	public int getCount5() {
		return count5;
	}

	public void setCount5(int count5) {
		this.count5 = count5;
	}

	public double getAmount5() {
		return amount5;
	}

	public void setAmount5(double amount5) {
		this.amount5 = amount5;
	}

	public int getCount6() {
		return count6;
	}

	public void setCount6(int count6) {
		this.count6 = count6;
	}

	public double getAmount6() {
		return amount6;
	}

	public void setAmount6(double amount6) {
		this.amount6 = amount6;
	}

	public int getCount7() {
		return count7;
	}

	public void setCount7(int count7) {
		this.count7 = count7;
	}

	public double getAmount7() {
		return amount7;
	}

	public void setAmount7(double amount7) {
		this.amount7 = amount7;
	}

	public int getCount8() {
		return count8;
	}

	public void setCount8(int count8) {
		this.count8 = count8;
	}

	public double getAmount8() {
		return amount8;
	}

	public void setAmount8(double amount8) {
		this.amount8 = amount8;
	}

	public int getCount9() {
		return count9;
	}

	public void setCount9(int count9) {
		this.count9 = count9;
	}

	public double getAmount9() {
		return amount9;
	}

	public void setAmount9(double amount9) {
		this.amount9 = amount9;
	}

	public int getCount10() {
		return count10;
	}

	public void setCount10(int count10) {
		this.count10 = count10;
	}

	public double getAmount10() {
		return amount10;
	}

	public void setAmount10(double amount10) {
		this.amount10 = amount10;
	}

	public int getCount11() {
		return count11;
	}

	public void setCount11(int count11) {
		this.count11 = count11;
	}

	public double getAmount11() {
		return amount11;
	}

	public void setAmount11(double amount11) {
		this.amount11 = amount11;
	}

	public int getCount12() {
		return count12;
	}

	public void setCount12(int count12) {
		this.count12 = count12;
	}

	public double getAmount12() {
		return amount12;
	}

	public void setAmount12(double amount12) {
		this.amount12 = amount12;
	}

	public int getCount13() {
		return count13;
	}

	public void setCount13(int count13) {
		this.count13 = count13;
	}

	public double getAmount13() {
		return amount13;
	}

	public void setAmount13(double amount13) {
		this.amount13 = amount13;
	}

	public int getCount14() {
		return count14;
	}

	public void setCount14(int count14) {
		this.count14 = count14;
	}

	public double getAmount14() {
		return amount14;
	}

	public void setAmount14(double amount14) {
		this.amount14 = amount14;
	}

	public int getCount15() {
		return count15;
	}

	public void setCount15(int count15) {
		this.count15 = count15;
	}

	public double getAmount15() {
		return amount15;
	}

	public void setAmount15(double amount15) {
		this.amount15 = amount15;
	}

	public int getCount16() {
		return count16;
	}

	public void setCount16(int count16) {
		this.count16 = count16;
	}

	public double getAmount16() {
		return amount16;
	}

	public void setAmount16(double amount16) {
		this.amount16 = amount16;
	}

	public int getCount17() {
		return count17;
	}

	public void setCount17(int count17) {
		this.count17 = count17;
	}

	public double getAmount17() {
		return amount17;
	}

	public void setAmount17(double amount17) {
		this.amount17 = amount17;
	}

	public int getCount18() {
		return count18;
	}

	public void setCount18(int count18) {
		this.count18 = count18;
	}

	public double getAmount18() {
		return amount18;
	}

	public void setAmount18(double amount18) {
		this.amount18 = amount18;
	}

	public int getCount19() {
		return count19;
	}

	public void setCount19(int count19) {
		this.count19 = count19;
	}

	public double getAmount19() {
		return amount19;
	}

	public void setAmount19(double amount19) {
		this.amount19 = amount19;
	}

	public int getCount20() {
		return count20;
	}

	public void setCount20(int count20) {
		this.count20 = count20;
	}

	public double getAmount20() {
		return amount20;
	}

	public void setAmount20(double amount20) {
		this.amount20 = amount20;
	}

	public int getCount21() {
		return count21;
	}

	public void setCount21(int count21) {
		this.count21 = count21;
	}

	public double getAmount21() {
		return amount21;
	}

	public void setAmount21(double amount21) {
		this.amount21 = amount21;
	}

	public int getCount22() {
		return count22;
	}

	public void setCount22(int count22) {
		this.count22 = count22;
	}

	public double getAmount22() {
		return amount22;
	}

	public void setAmount22(double amount22) {
		this.amount22 = amount22;
	}

	public int getCount23() {
		return count23;
	}

	public void setCount23(int count23) {
		this.count23 = count23;
	}

	public double getAmount23() {
		return amount23;
	}

	public void setAmount23(double amount23) {
		this.amount23 = amount23;
	}

	public int getCount24() {
		return count24;
	}

	public void setCount24(int count24) {
		this.count24 = count24;
	}

	public double getAmount24() {
		return amount24;
	}

	public void setAmount24(double amount24) {
		this.amount24 = amount24;
	}

	public int getCount25() {
		return count25;
	}

	public void setCount25(int count25) {
		this.count25 = count25;
	}

	public double getAmount25() {
		return amount25;
	}

	public void setAmount25(double amount25) {
		this.amount25 = amount25;
	}

	public int getCount26() {
		return count26;
	}

	public void setCount26(int count26) {
		this.count26 = count26;
	}

	public double getAmount26() {
		return amount26;
	}

	public void setAmount26(double amount26) {
		this.amount26 = amount26;
	}

	public int getCount27() {
		return count27;
	}

	public void setCount27(int count27) {
		this.count27 = count27;
	}

	public double getAmount27() {
		return amount27;
	}

	public void setAmount27(double amount27) {
		this.amount27 = amount27;
	}

	public int getCount28() {
		return count28;
	}

	public void setCount28(int count28) {
		this.count28 = count28;
	}

	public double getAmount28() {
		return amount28;
	}

	public void setAmount28(double amount28) {
		this.amount28 = amount28;
	}

	public int getCount29() {
		return count29;
	}

	public void setCount29(int count29) {
		this.count29 = count29;
	}

	public double getAmount29() {
		return amount29;
	}

	public void setAmount29(double amount29) {
		this.amount29 = amount29;
	}

	public int getCount30() {
		return count30;
	}

	public void setCount30(int count30) {
		this.count30 = count30;
	}

	public double getAmount30() {
		return amount30;
	}

	public void setAmount30(double amount30) {
		this.amount30 = amount30;
	}

	public int getCount31() {
		return count31;
	}

	public void setCount31(int count31) {
		this.count31 = count31;
	}

	public double getAmount31() {
		return amount31;
	}

	public void setAmount31(double amount31) {
		this.amount31 = amount31;
	}

	public int getCount32() {
		return count32;
	}

	public void setCount32(int count32) {
		this.count32 = count32;
	}

	public double getAmount32() {
		return amount32;
	}

	public void setAmount32(double amount32) {
		this.amount32 = amount32;
	}

	public int getCount33() {
		return count33;
	}

	public void setCount33(int count33) {
		this.count33 = count33;
	}

	public double getAmount33() {
		return amount33;
	}

	public void setAmount33(double amount33) {
		this.amount33 = amount33;
	}

	public int getCount34() {
		return count34;
	}

	public void setCount34(int count34) {
		this.count34 = count34;
	}

	public double getAmount34() {
		return amount34;
	}

	public void setAmount34(double amount34) {
		this.amount34 = amount34;
	}

	public int getCount35() {
		return count35;
	}

	public void setCount35(int count35) {
		this.count35 = count35;
	}

	public double getAmount35() {
		return amount35;
	}

	public void setAmount35(double amount35) {
		this.amount35 = amount35;
	}

	public int getCount36() {
		return count36;
	}

	public void setCount36(int count36) {
		this.count36 = count36;
	}

	public double getAmount36() {
		return amount36;
	}

	public void setAmount36(double amount36) {
		this.amount36 = amount36;
	}

	public int getCount37() {
		return count37;
	}

	public void setCount37(int count37) {
		this.count37 = count37;
	}

	public double getAmount37() {
		return amount37;
	}

	public void setAmount37(double amount37) {
		this.amount37 = amount37;
	}

	public int getCount38() {
		return count38;
	}

	public void setCount38(int count38) {
		this.count38 = count38;
	}

	public double getAmount38() {
		return amount38;
	}

	public void setAmount38(double amount38) {
		this.amount38 = amount38;
	}

	public int getCount39() {
		return count39;
	}

	public void setCount39(int count39) {
		this.count39 = count39;
	}

	public double getAmount39() {
		return amount39;
	}

	public void setAmount39(double amount39) {
		this.amount39 = amount39;
	}

	public int getCount40() {
		return count40;
	}

	public void setCount40(int count40) {
		this.count40 = count40;
	}

	public double getAmount40() {
		return amount40;
	}

	public void setAmount40(double amount40) {
		this.amount40 = amount40;
	}

	public int getCount41() {
		return count41;
	}

	public void setCount41(int count41) {
		this.count41 = count41;
	}

	public double getAmount41() {
		return amount41;
	}

	public void setAmount41(double amount41) {
		this.amount41 = amount41;
	}

	public int getCount42() {
		return count42;
	}

	public void setCount42(int count42) {
		this.count42 = count42;
	}

	public double getAmount42() {
		return amount42;
	}

	public void setAmount42(double amount42) {
		this.amount42 = amount42;
	}

	public int getCount43() {
		return count43;
	}

	public void setCount43(int count43) {
		this.count43 = count43;
	}

	public double getAmount43() {
		return amount43;
	}

	public void setAmount43(double amount43) {
		this.amount43 = amount43;
	}

	public int getCount44() {
		return count44;
	}

	public void setCount44(int count44) {
		this.count44 = count44;
	}

	public double getAmount44() {
		return amount44;
	}

	public void setAmount44(double amount44) {
		this.amount44 = amount44;
	}

	public int getCount45() {
		return count45;
	}

	public void setCount45(int count45) {
		this.count45 = count45;
	}

	public double getAmount45() {
		return amount45;
	}

	public void setAmount45(double amount45) {
		this.amount45 = amount45;
	}

	public int getCount46() {
		return count46;
	}

	public void setCount46(int count46) {
		this.count46 = count46;
	}

	public double getAmount46() {
		return amount46;
	}

	public void setAmount46(double amount46) {
		this.amount46 = amount46;
	}

	public int getCount47() {
		return count47;
	}

	public void setCount47(int count47) {
		this.count47 = count47;
	}

	public double getAmount47() {
		return amount47;
	}

	public void setAmount47(double amount47) {
		this.amount47 = amount47;
	}

	public int getCount48() {
		return count48;
	}

	public void setCount48(int count48) {
		this.count48 = count48;
	}

	public double getAmount48() {
		return amount48;
	}

	public void setAmount48(double amount48) {
		this.amount48 = amount48;
	}

	public int getCount49() {
		return count49;
	}

	public void setCount49(int count49) {
		this.count49 = count49;
	}

	public double getAmount49() {
		return amount49;
	}

	public void setAmount49(double amount49) {
		this.amount49 = amount49;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public int getShiftBatchNo() {
		return shiftBatchNo;
	}

	public void setShiftBatchNo(int shiftBatchNo) {
		this.shiftBatchNo = shiftBatchNo;
	}

	public String getTripNo() {
		return tripNo;
	}

	public void setTripNo(String tripNo) {
		this.tripNo = tripNo;
	}

	public String getPaymentHostBatchNo() {
		return paymentHostBatchNo;
	}

	public void setPaymentHostBatchNo(String paymentHostBatchNo) {
		this.paymentHostBatchNo = paymentHostBatchNo;
	}

	public String getPaymentHostTid() {
		return paymentHostTid;
	}

	public void setPaymentHostTid(String paymentHostTid) {
		this.paymentHostTid = paymentHostTid;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getMasterVersion() {
		return masterVersion;
	}

	public void setMasterVersion(String masterVersion) {
		this.masterVersion = masterVersion;
	}

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public String getDeviceModelNo() {
		return deviceModelNo;
	}

	public void setDeviceModelNo(String deviceModelNo) {
		this.deviceModelNo = deviceModelNo;
	}

	public String getDeviceComponentVersion() {
		return deviceComponentVersion;
	}

	public void setDeviceComponentVersion(String deviceComponentVersion) {
		this.deviceComponentVersion = deviceComponentVersion;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
