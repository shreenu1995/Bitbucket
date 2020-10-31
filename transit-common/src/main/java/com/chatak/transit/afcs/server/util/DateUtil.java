package com.chatak.transit.afcs.server.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static Timestamp setGenerationDateTimeResponse(Timestamp generationDateStart) {
		if (generationDateStart != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(generationDateStart.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(generationDateStart.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(generationDateStart.toString().substring(8, 10)));
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(generationDateStart.toString().substring(11, 13)) + 6);
			time.set(Calendar.MINUTE, Integer.parseInt(generationDateStart.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(generationDateStart.toString().substring(17, 19)));
			return Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			return null;
		}
	}

	public static Timestamp setGenerationDateStartRequest(Timestamp generationDateStart) {
		if (generationDateStart != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar time = Calendar.getInstance();
			time.set(Calendar.YEAR, Integer.parseInt(generationDateStart.toString().substring(0, 4)));
			time.set(Calendar.MONTH, Integer.parseInt(generationDateStart.toString().substring(5, 7)) - 1);
			time.set(Calendar.DATE, Integer.parseInt(generationDateStart.toString().substring(8, 10)) - 1);
			time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(generationDateStart.toString().substring(11, 13)) + 19);
			time.set(Calendar.MINUTE, Integer.parseInt(generationDateStart.toString().substring(14, 16)) - 30);
			time.set(Calendar.SECOND, Integer.parseInt(generationDateStart.toString().substring(17, 19)));
			return Timestamp.valueOf(sdf.format(time.getTime()));
		} else {
			return null;
		}
	}

	private DateUtil() {
		super();
	}

	public static Timestamp convertStringToTimestamp(String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return new Timestamp((df.parse(date)).getTime());
		} catch (Exception e) {
			logger.error("DateUtil class :: convertStringToTimestamp method :: exception", e);
		}
		return null;
	}
	
	public static String toDateStringFormat(Timestamp date) {
		if (null != date) {
			String sd = date.toString().substring(0, 10);
			StringBuilder dateString = new StringBuilder();
			return dateString.append(sd.substring(8, 10)).append("/").append(sd.substring(5, 7)).append("/")
					.append(sd.substring(0, 4)).toString();
		} else {
			return null;
		}
	}
	
	public static Timestamp toTimestamp(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		try {
			dateFormat.applyPattern(pattern);
			return new Timestamp(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			logger.info("ERROR:: DateUtil :: toTimestamp method : with date : " + date, e);
		}
		return null;
	}

}
