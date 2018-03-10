package ua.compservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingPlace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trading_place_id")
	private Long id;
	
	@Column(name = "code")
	@JsonProperty(value = "Код")
	private String code;
	
	@Column(name = "desc")
	@JsonProperty(value = "Наименование")
	private String description;
	
	
}
