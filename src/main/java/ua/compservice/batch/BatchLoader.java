package ua.compservice.batch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.compservice.model.Client;
import ua.compservice.model.Contract;
import ua.compservice.model.ContractCredit;
import ua.compservice.model.Counter;
import ua.compservice.model.CounterPayment;
import ua.compservice.model.CounterValuesOnPeriod;
import ua.compservice.model.ServiceType;
import ua.compservice.model.TradingPlace;
import ua.compservice.repository.ClientRepository;
import ua.compservice.repository.ContractCreditRepository;
import ua.compservice.repository.ContractRepository;
import ua.compservice.repository.CounterPaymentRepository;
import ua.compservice.repository.CounterRepository;
import ua.compservice.repository.CounterValuesOnPeriodRepository;
import ua.compservice.repository.TradingPlaceRepository;

public class BatchLoader {

	
	private static final Logger logger = LoggerFactory.getLogger(BatchLoader.class);
	
	private CounterRepository counterRepository;
	private CounterPaymentRepository counterPaymentRepository;
	private CounterValuesOnPeriodRepository counterValueRepository;
	private TradingPlaceRepository tradingPlaceRepository;
	private ContractRepository contractRepository;
	private ClientRepository clientRepository;
	private ContractCreditRepository contractCreditRepository;
	
	
	
	@Autowired
	public void setCounterRepository(CounterRepository counterRepository) {
		this.counterRepository = counterRepository;
	}

	@Autowired
	public void setCounterPaymentRepository(CounterPaymentRepository counterPaymentRepository) {
		this.counterPaymentRepository = counterPaymentRepository;
	}

	@Autowired
	public void setCounterValueRepository(CounterValuesOnPeriodRepository counterValueRepository) {
		this.counterValueRepository = counterValueRepository;
	}

	@Autowired
	public void setTradingPlaceRepository(TradingPlaceRepository tradingPlaceRepository) {
		this.tradingPlaceRepository = tradingPlaceRepository;
	}

	@Autowired
	public void setContractRepository(ContractRepository contractRepository) {
		this.contractRepository = contractRepository;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	
	@Autowired
	public void setContractCreditRepository(ContractCreditRepository contractCreditRepository) {
		this.contractCreditRepository = contractCreditRepository;
	}
	
	

	@SuppressWarnings("unused")
	public void loadCounterPayments() throws IOException {
		File json = ResourceUtils.getFile("classpath:counterPayments.json");
		
		String content = new String(Files.readAllBytes(json.toPath()));
		
		ObjectMapper om = new ObjectMapper();
		CounterPayment[] payments = om.readValue(content, CounterPayment[].class);
		
		Arrays.asList(payments)
			.stream()
			.forEach(p -> {
				
				Counter counter = counterRepository.findByCode(p.getCounter().getCode());
				
				if (counter != null) {
					
					CounterPayment payment = counterPaymentRepository.findByDateAndCounter(p.getDate(), counter);
					
					if (payment == null) {
						payment = counterPaymentRepository.save(new CounterPayment(null, p.getDate(), counter, p.getAmount()));
					}
					
				}
			});
		
	}
	
	@SuppressWarnings("unused")
	public void loadCounterValues()
			throws IOException, JsonParseException, JsonMappingException {
		File json = ResourceUtils.getFile("classpath:counterValues.json");
		
		String content = new String(Files.readAllBytes(json.toPath()));
		
		ObjectMapper om = new ObjectMapper();
		
		CounterValuesOnPeriod[] cv = om.readValue(content, CounterValuesOnPeriod[].class);
		
		Arrays.asList(cv)
			.stream()
			.forEach(v -> {
				
				Counter counter = counterRepository.findByCode(v.getCounter().getCode());
				
				if (counter != null) {
					LocalDate period = v.getPeriod();
					
					if (period != null) {
						//only if we have both (counter and period) we can write to the db...
						CounterValuesOnPeriod cvOnPeriod = counterValueRepository.findByPeriodAndCounter(period, counter);
						if (cvOnPeriod == null) {
							cvOnPeriod = counterValueRepository.save(new CounterValuesOnPeriod(null, counter, period, v.getValue()));
						}
						
					}
				}
				
			});
	}

	@SuppressWarnings("unused")
	public void loadContractInfo() throws IOException {
		
		File file = ResourceUtils.getFile("classpath:counters.json");
		
		String contentJson = new String(Files.readAllBytes(file.toPath()));
		
		ObjectMapper om = new ObjectMapper();

		Contract[] contracts = om.readValue(contentJson, Contract[].class);

		Arrays.asList(contracts).stream().forEach(c -> {
			
					
			TradingPlace tp = tradingPlaceRepository.findByCode(c.getTradingPlace().getCode());
			
			if (tp == null) {
				tp = tradingPlaceRepository.save(c.getTradingPlace());
			}
	
			Client client = clientRepository.findByCode(c.getClient().getCode());
			
			if (client == null) {
				client = clientRepository.save(c.getClient());
			}
			
			Contract contract = contractRepository.findByNumber(c.getNumber());
			
			if (contract == null) {
				contract = contractRepository.save(
				new Contract(null, c.getNumber(), c.getDate(), c.getContractNumber(),
						tp, client, c.getPaymentDate(), c.getFinishedDate(), c.getSectorNumber(), c.getCounters()));
			}
			
			

		});
	
		
	}
	
	@SuppressWarnings("unused")
	public void loadContractCredit() throws IOException {
		final String NO_CODE = "NO_CODE";
		
		File json = ResourceUtils.getFile("classpath:credit.json");
		
		String content = new String(Files.readAllBytes(json.toPath()));
		
		
		ObjectMapper om = new ObjectMapper();
		
		ContractCredit[] contracts = om.readValue(content, ContractCredit[].class);
		
		Arrays.asList(contracts)
			.stream()
			.forEach(c -> {
				
				Contract contract = contractRepository.findByNumber(c.getContract().getNumber());
				
				
				ContractCredit contractCredit = null;
				
				if (contract != null) {
					TradingPlace tradingPlace = tradingPlaceRepository.findByCode(c.getTradingPlace().getCode());
					
					Client client = clientRepository.findByCode(c.getClient().getCode());
					
					Counter counter = counterRepository.findByCode(c.getCounter() != null ? c.getCounter().getCode(): NO_CODE);
					
					if ((counter == null) && (c.getService() != ServiceType.ELECTRICITY)) {
						
						contractCredit = contractCreditRepository.findByDateAndContractAndService(c.getDate(), contract, c.getService());
						
						if (contractCredit == null) {
							contractCredit = contractCreditRepository.save(new ContractCredit(null, c.getDate(), contract, client, tradingPlace, c.getService(), null, c.getCredit()));
						}
						
					} else if ((counter != null) && (c.getService() == ServiceType.ELECTRICITY)) {
						contractCredit = contractCreditRepository.findByDateAndContractAndServiceAndCounter(c.getDate(), contract, c.getService(), counter);
						
						if (contractCredit == null) {
							contractCredit = contractCreditRepository.save(new ContractCredit(null, c.getDate(), contract, client, tradingPlace, c.getService(), counter, c.getCredit()));
						}
					} else {
						logger.warn("For the contract {} and the service {} type service is mismatched", contract, c.getService()); 
					}
					
					
				}
				
				
				
				
			});
		
		
		
		
	}
}
