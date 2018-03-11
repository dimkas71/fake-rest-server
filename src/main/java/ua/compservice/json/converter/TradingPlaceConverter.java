package ua.compservice.json.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.compservice.model.TradingPlace;

public class TradingPlaceConverter extends StdConverter<String, TradingPlace>{

	@Override
	public TradingPlace convert(String code) {
		if ("".equals(code)) return null;
		return new TradingPlace(null, code, "");
	}

}
