package ua.compservice.model.client;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.model.ServiceType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfo {

	private Long id;
	
	private String number;
	private String owner;
	private int sectorNumber;
	
	private List<CounterInfo> counters;
	
	private Map<ServiceType, Double> debts;
	
}
