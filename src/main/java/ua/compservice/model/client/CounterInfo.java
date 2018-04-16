package ua.compservice.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterInfo {
	
	private Long id;
	
	private String description;
	private String factoryNumber;
	private int limit;
	private String contractNumber;
	
	private Long value;
	

}
