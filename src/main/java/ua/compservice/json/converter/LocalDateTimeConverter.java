package ua.compservice.json.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateTimeConverter extends StdConverter<String, LocalDateTime>{

	@Override
	public LocalDateTime convert(String formattedDateTime) {
		return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(formattedDateTime));
	}

}
