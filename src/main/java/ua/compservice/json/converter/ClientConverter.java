package ua.compservice.json.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.compservice.model.Client;

public class ClientConverter extends StdConverter<String, Client> {

	@Override
	public Client convert(String clientCode) {
		if ("".equals(clientCode)) return null;
		return new Client(null, clientCode, "", "", "");
	}

}
