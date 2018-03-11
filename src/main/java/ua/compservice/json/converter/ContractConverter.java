package ua.compservice.json.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import ua.compservice.model.Contract;

public class ContractConverter extends StdConverter<String, Contract>{

	@Override
	public Contract convert(String innerContractNumber) {
		if ("".equals(innerContractNumber)) return null;
		return new Contract(null, innerContractNumber, null, null, null, null, null, null, 0, null);
	}

}
