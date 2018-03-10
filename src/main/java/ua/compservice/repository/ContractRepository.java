package ua.compservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{
	Contract findByNumber(String number);
}
