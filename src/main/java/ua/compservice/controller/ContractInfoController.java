package ua.compservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.compservice.model.Contract;
import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;
import ua.compservice.model.client.ContractInfo;
import ua.compservice.repository.ContractCreditRepository;
import ua.compservice.repository.ContractRepository;
import ua.compservice.repository.CounterValuesOnPeriodRepository;

@RestController
@RequestMapping("/contractinfo")
public class ContractInfoController {

	private static final Logger logger = LoggerFactory.getLogger(ContractInfoController.class.getSimpleName());
	
	@Autowired
	private ContractRepository contractRepos;
	
	@Autowired
	private ContractCreditRepository creditRepos; 
	
	@Autowired
	private CounterValuesOnPeriodRepository counterValuesRepos;
	
	
	@GetMapping("{number}")
	public List<ContractInfo> infos(@PathVariable("number") String number) {
		
		List<Contract> contracts = contractRepos.findByNumberStartingWith(number);
		
		List<Counter> counters = contracts.stream()
				.flatMap(c -> c.getCounters().stream())
				.collect(Collectors.toList());

		List<CounterValuesOnPeriod> counterValues = counterValuesRepos.latestValuesOf(counters);
		
		logger.info("Counter values by period: {}", counterValues);
		
		return contracts.stream()
			.map(c -> new ContractInfo(c.getId(), c.getNumber(), c.getClient().getDescription(), c.getSectorNumber(), null, null))
			.collect(Collectors.toList());
		
	}
	
	
	
}
