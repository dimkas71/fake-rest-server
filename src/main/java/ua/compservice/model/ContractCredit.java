package ua.compservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.json.converter.ClientConverter;
import ua.compservice.json.converter.ContractConverter;
import ua.compservice.json.converter.CounterConverter;
import ua.compservice.json.converter.LocalDateTimeConverter;
import ua.compservice.json.converter.ServiceConverter;
import ua.compservice.json.converter.TradingPlaceConverter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCredit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("Период")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime date;
	
	@OneToOne
	@JsonProperty("НомерДоговора")
	@JsonDeserialize(converter = ContractConverter.class)
	private Contract contract;
	
	@OneToOne
	@JsonProperty("КодКонтрагента")
	@JsonDeserialize(converter = ClientConverter.class)
	private Client client;
		
	@OneToOne
	@JsonProperty("КодТорговогоМеста")
	@JsonDeserialize(converter = TradingPlaceConverter.class)
	private TradingPlace tradingPlace;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty("ТипУслуги")
	@JsonDeserialize(converter = ServiceConverter.class)
	private ServiceType service;
	
	@OneToOne
	@JsonProperty("КодСчетчика")
	@JsonDeserialize(converter = CounterConverter.class)
	private Counter counter;
	
	@JsonProperty("Долг")
	private double credit;
	

}
