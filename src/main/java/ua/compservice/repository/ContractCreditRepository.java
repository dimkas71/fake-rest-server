package ua.compservice.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.compservice.model.Contract;
import ua.compservice.model.ContractCredit;
import ua.compservice.model.Counter;
import ua.compservice.model.ServiceType;

public interface ContractCreditRepository extends JpaRepository<ContractCredit, Long> {
	ContractCredit findByDateAndContractAndServiceAndCounter(LocalDateTime d, Contract contract, ServiceType service,
			Counter counter);

	ContractCredit findByDateAndContractAndService(LocalDateTime d, Contract contract, ServiceType service);

	@Query(value = "select id, credit, contract.date, contract.service, client_id, contract.contract_id, counter_id, trading_place_trading_place_id from contract_credit as contract\n"
			+ "inner join\n" + "(SELECT max(date) as date, contract_id, service FROM CONTRACT_CREDIT \n"
			+ "where contract_id in :contracts\n" + "group by contract_id, service) as inner_data\n" + "on \n"
			+ "contract.date = inner_data.date\n" + "and contract.contract_id = inner_data.contract_id\n"
			+ "and contract.service = inner_data.service", nativeQuery = true)
	List<ContractCredit> latestValuesOf(@Param("contracts") Collection<Contract> contracts);

}
