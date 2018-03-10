package ua.compservice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.json.converter.LocalDateTimeConverter;

@Entity
@Table(name = "counters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Counter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="counter_code", unique = true)
	@JsonProperty("Код")
	private String code;
	
	@Column(name="counter_desc")
	@JsonProperty("Наименование")
	private String description;
	
	@JsonProperty("ДатаНачалаЭксплуатации")
	@JsonDeserialize(converter = LocalDateTimeConverter.class)
	private LocalDateTime beginDate;
	
	@JsonProperty("ЗаводскойНомер")
	private String factoryNumber;
	
	@JsonProperty("КоэффициентКТарифу")
	private float coefficient;
	
	@JsonProperty("Лимит")
	@Column(name = "counter_limit")
	private int limit;
	
	@JsonProperty("ТипСчетчика")
	private String counterType;
	
	@JsonProperty("УчитыватьЭффективнуюМощность")
	private boolean useReactivePower;
	
	@JsonProperty("НомерДоговора")
	private String contractNumber;
	
}
