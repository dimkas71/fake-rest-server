package ua.compservice.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;

public interface CounterValuesOnPeriodRepository extends JpaRepository<CounterValuesOnPeriod, Long>{
	
	CounterValuesOnPeriod findByPeriodAndCounter(LocalDate period, Counter c);

}
