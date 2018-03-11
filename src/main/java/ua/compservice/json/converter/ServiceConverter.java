package ua.compservice.json.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.compservice.model.ServiceType;

public class ServiceConverter extends StdConverter<String, ServiceType>{

	@Override
	public ServiceType convert(String value) {
		return ServiceType.of(value);
	}

}
