package ua.compservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Client;
import ua.compservice.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{
	
	Contract findByNumber(String number);
	
	List<Contract> findByClient(Client client);
	
	List<Contract> findByClientCode(String code);
	
	
}
