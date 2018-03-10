package ua.compservice.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.json.converter.LocalDateTimeConverter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "contract_inner_number")
	@JsonProperty("Номер")
	private String number;
	
	@JsonProperty("Дата")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime date;
	
	@JsonProperty("НомерДоговора")
	private String contractNumber;
	
	@OneToOne
	@JsonProperty("ТорговоеМесто")
	private TradingPlace tradingPlace;
	
	@OneToOne
	@JsonProperty("Контрагент")
	private Client client;
	
	@JsonProperty("ДатаНачалаОплаты")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime paymentDate;
	
	@JsonProperty("ДатаОкончания")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime finishedDate;
	
	@JsonProperty("НомерСектора")
	private int sectorNumber;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonProperty("Счетчики")
	private List<Counter> counters;
		
	
}
