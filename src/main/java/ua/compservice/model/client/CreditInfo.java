package ua.compservice.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.model.ServiceType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditInfo {

	private Long id; // Contract's id...
	private ServiceType service;
	private String counter; // ???
	private Double credit;

}
