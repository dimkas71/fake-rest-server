package ua.compservice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.compservice.model.Contract;
import ua.compservice.model.ContractCredit;
import ua.compservice.model.Counter;
import ua.compservice.model.CounterPayment;
import ua.compservice.model.CounterValuesOnPeriod;
import ua.compservice.model.ServiceType;
import ua.compservice.model.client.ContractCounterMeterInfo;
import ua.compservice.model.client.ContractCounterPaymentInfo;
import ua.compservice.model.client.ContractInfo;
import ua.compservice.model.client.CounterInfo;
import ua.compservice.model.client.CreditInfo;
import ua.compservice.repository.ContractCreditRepository;
import ua.compservice.repository.ContractRepository;
import ua.compservice.repository.CounterPaymentRepository;
import ua.compservice.repository.CounterRepository;
import ua.compservice.repository.CounterValuesOnPeriodRepository;

@RestController
@RequestMapping("/contractinfo")
public class ContractInfoController {

	private static final Logger logger = LoggerFactory.getLogger(ContractInfoController.class.getSimpleName());
	
	@Autowired
	private ContractRepository contractRepos;
	
	@Autowired
	private CounterValuesOnPeriodRepository counterValuesRepos;

	@Autowired
	private ContractCreditRepository contractCreditRepos;

	@Autowired
	private CounterRepository counterRepository;
	
	@Autowired
	private CounterPaymentRepository counterPaymentRepo;
	
	@GetMapping("{contract_number}")
	public List<ContractInfo> list(@PathVariable("contract_number") String contractNumber) {

		List<Contract> contracts = contractRepos.findByNumberStartingWith(contractNumber);

		List<Counter> counters = contracts.stream().flatMap(c -> c.getCounters().stream()).collect(Collectors.toList());

		List<CounterValuesOnPeriod> counterValues = counterValuesRepos.latestValuesOf(counters);

		List<ContractCredit> contractCredits = contractCreditRepos.latestValuesOf(contracts);

		List<ContractInfo> contractInfos = new ArrayList<>();

		contractInfos = contracts.stream()
				.map(c -> new ContractInfo(c.getId(), c.getNumber(), c.getClient().getDescription(),
						c.getSectorNumber(),
						counterValues.stream().filter(cv -> c.getCounters().contains(cv.getCounter()))
								.map(cv -> new CounterInfo(cv.getCounter().getId(), cv.getCounter().getFactoryNumber(),
										cv.getValue()))
								.collect(Collectors.toList()),
						contractCredits.stream().filter(cc -> cc.getContract().equals(c))
								.map(cc -> new CreditInfo(cc.getContract().getId(), cc.getService(),
										cc.getService() == ServiceType.ELECTRICITY ? cc.getCounter().getFactoryNumber()
												: "",
										cc.getCredit()))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());

		return contractInfos;

	}


	@GetMapping("{id}/meter")
	public List<ContractCounterMeterInfo> counterMetersByPeriod(@PathVariable("id") Long counterId, @PathParam("begin") String begin, @PathParam("end") String end) {
		
		Counter counter = counterRepository.findOne(counterId);
		
		LocalDate from = LocalDate.parse(begin, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate to = LocalDate.parse(end, DateTimeFormatter.BASIC_ISO_DATE);
		
		List<CounterValuesOnPeriod> countersByPeriod = counterValuesRepos.findByPeriodBetweenAndCounter(from, to, counter);
		
		
		return countersByPeriod.stream()
			.map(cbp -> new ContractCounterMeterInfo(counter.getId(), cbp.getPeriod(), cbp.getValue()))
			.sorted(Comparator.comparing(ContractCounterMeterInfo::getPeriod))
			.collect(Collectors.toList());
	}
	
	
	@GetMapping("{id}/payment")
	public List<ContractCounterPaymentInfo> counterPaymentByPeriod(@PathVariable("id") Long counterId, @PathParam("begin") String begin, @PathParam("end") String end) {
		
		Counter counter = counterRepository.findOne(counterId);
		
		LocalDateTime from = LocalDate.parse(begin, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay();
		LocalDateTime to = LocalDate.parse(end, DateTimeFormatter.BASIC_ISO_DATE).plusDays(1).atStartOfDay();
		
		List<CounterPayment> counterPayments = counterPaymentRepo.findByDateBetweenAndCounter(from, to, counter);
		
		
		return counterPayments.stream()
			.map(cp -> new ContractCounterPaymentInfo(counter.getId(), cp.getDate().toLocalDate(), cp.getAmount()))
			.sorted(Comparator.comparing(ContractCounterPaymentInfo::getPeriod))
			.collect(Collectors.toList());
	}
	
	
}
