package ua.compservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {
	
	Counter findByCode(String code);

}
