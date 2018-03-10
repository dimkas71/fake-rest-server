package ua.compservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.TradingPlace;

public interface TradingPlaceRepository extends JpaRepository<TradingPlace, Long> {
	
	TradingPlace findByCode(String code);
	TradingPlace findByDescription(String description);
	

}
