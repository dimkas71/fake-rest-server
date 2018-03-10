package ua.compservice.json.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.compservice.model.Counter;

public class CounterConverter extends StdConverter<String, Counter> {

	@Override
	public Counter convert(String code) {
		//Create some fake object that holding a counter code here...
		return new Counter(null, code, null, null, null, 0.0f, 0, null, false, null);
	}

}
