package ua.compservice.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterInfo {

	private Long id;

	private String factoryNumber;
	private Long value;

}
