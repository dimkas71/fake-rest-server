package ua.compservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.compservice.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{
	
	Client findByCode(String code);
	Client findByDescription(String description);
	List<Client> findByTaxCode(String taxCode);
	
}
