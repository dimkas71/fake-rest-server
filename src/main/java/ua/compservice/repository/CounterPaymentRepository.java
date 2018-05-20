package ua.compservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Counter;
import ua.compservice.model.CounterPayment;

public interface CounterPaymentRepository extends JpaRepository<CounterPayment, Long>{
	
	CounterPayment findByDateAndCounter(LocalDateTime d, Counter c);
	
	List<CounterPayment> findByDateBetweenAndCounter(LocalDateTime from, LocalDateTime to, Counter c);	
	

}
