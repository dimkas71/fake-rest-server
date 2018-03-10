package ua.compservice.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.json.converter.CounterConverter;
import ua.compservice.json.converter.LocalDateConverter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CounterValuesOnPeriod {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JsonProperty("Код")
	@JsonDeserialize(converter = CounterConverter.class)
	private Counter counter;
	
	@JsonProperty("Период")
	@JsonDeserialize(converter = LocalDateConverter.class)
	private LocalDate period;
	
	@JsonProperty("Показание")
	private Long value;
	
	
	

}
