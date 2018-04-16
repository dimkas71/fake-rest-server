package ua.compservice.model.client;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfo {

	private Long id;
	
	private String number;
	private String owner;
	private int sectorNumber;
	
	private List<CounterInfo> counters;
	
	private List<CreditInfo> credits;


}
