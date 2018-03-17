package ua.compservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.compservice.model.Client;
import ua.compservice.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{
	
	Contract findByNumber(String number);
	
	List<Contract> findByClient(Client client);
	
	@Query("select c from Contract c where c.client.code = ?1")
	List<Contract> findByClientCode(String code);
	
	List<Contract> findByNumberStartingWith(String number);
	
	
}
