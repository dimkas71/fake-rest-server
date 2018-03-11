package ua.compservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.json.converter.CounterConverter;
import ua.compservice.json.converter.LocalDateTimeConverter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("Период")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime date;
	
	@OneToOne
	@JsonProperty("Код")
	@JsonDeserialize(converter = CounterConverter.class)
	private Counter counter;
	
	
	@JsonProperty("Сумма")
	private double amount;

}
