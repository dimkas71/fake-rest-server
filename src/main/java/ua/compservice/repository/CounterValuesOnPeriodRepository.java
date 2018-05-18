package ua.compservice.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;

public interface CounterValuesOnPeriodRepository extends JpaRepository<CounterValuesOnPeriod, Long> {

	CounterValuesOnPeriod findByPeriodAndCounter(LocalDate period, Counter c);
	
	@Query(value = "select cvop.id, cvop.period, cvop.value, cvop.counter_id from COUNTER_VALUES_ON_PERIOD as cvop \n"
			+ "inner join (SELECT max(period) as period, counter_id as counter_id FROM COUNTER_VALUES_ON_PERIOD \n"
			+ "where counter_id in :counters\n" + "group by counter_id) inner_data\n"
			+ "on cvop.period = inner_data.period\n"
			+ "and cvop.counter_id = inner_data.counter_id", nativeQuery = true)
	List<CounterValuesOnPeriod> latestValuesOf(@Param("counters") Collection<Counter> counters);

	List<CounterValuesOnPeriod> findByPeriodBetweenAndCounter(LocalDate from, LocalDate to, Counter c);	
	

}
