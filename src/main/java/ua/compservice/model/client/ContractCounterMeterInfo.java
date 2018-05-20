package ua.compservice.model.client;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCounterMeterInfo {
	
	private Long counterId;
	private LocalDate period;
	private Long value;
	

}
