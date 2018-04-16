package ua.compservice.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;

public interface CounterValuesOnPeriodRepository extends JpaRepository<CounterValuesOnPeriod, Long>{
	
	CounterValuesOnPeriod findByPeriodAndCounter(LocalDate period, Counter c);
	
	
	
	//@Query("select cv from CounterValuesOnPeriod cv where cv.counter in :counters")
	
	@Query("select cv from CounterValuesOnPeriod cv\n" + 
			"inner join (select cv.id id, max(cv.period) period from CounterValuesOnPeriod cv where cv.counter in :counters) tb\n" + 
			"on cv.id = tb.id and cv.period = tb.period")
	List<CounterValuesOnPeriod> latestValuesOf(@Param("counters") Collection<Counter> counters);

}
