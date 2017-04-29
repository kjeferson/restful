package br.example.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.h2.util.LocalDateTimeUtils;

public final class FeriadoUtil {

	public static String dateToStringIso8601Format(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.format(date);
	}

	public static Date stringToDateIso8601Format(String data) {
		return (Date) Date.from(LocalDateTime.parse(data, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static boolean dateIsSaturday(String date){	
		return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME).getDayOfWeek().compareTo(DayOfWeek.SATURDAY) == 1;
	}
	
	public static boolean dateIsSunday(String date){	
		return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME).getDayOfWeek().compareTo(DayOfWeek.SUNDAY) == 1;
	}
}
