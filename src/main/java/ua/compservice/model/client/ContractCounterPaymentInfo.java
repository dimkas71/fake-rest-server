package ua.compservice.model.client;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCounterPaymentInfo {

	private Long counterId;
	private LocalDate period;
	private double amount;
	
}
