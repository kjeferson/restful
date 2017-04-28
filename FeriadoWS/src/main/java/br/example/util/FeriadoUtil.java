package br.example.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class FeriadoUtil {

	public static String dateToStringIso8601Format(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.format(date);
	}

	public static Date stringToDateIso8601Format(String data) {
		return (Date) Date.from(LocalDateTime.parse(data, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				.atZone(ZoneId.systemDefault()).toInstant());
	}
}
