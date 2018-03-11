package ua.compservice.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Contract;
import ua.compservice.model.ContractCredit;
import ua.compservice.model.Counter;
import ua.compservice.model.ServiceType;

public interface ContractCreditRepository extends JpaRepository<ContractCredit, Long>{
	ContractCredit findByDateAndContractAndServiceAndCounter(LocalDateTime d, Contract contract, ServiceType service, Counter counter);
	ContractCredit findByDateAndContractAndService(LocalDateTime d, Contract contract, ServiceType service);
}
