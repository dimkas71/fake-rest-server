package ua.compservice.json.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.util.StdConverter;

public class LocalDateConverter extends StdConverter<String, LocalDate> {

	@Override
	public LocalDate convert(String formattedDate) {
		return LocalDate.from(DateTimeFormatter.ISO_DATE_TIME.parse(formattedDate));
	}

}
