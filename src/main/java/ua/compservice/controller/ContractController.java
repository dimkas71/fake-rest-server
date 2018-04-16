package ua.compservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.compservice.model.Contract;
import ua.compservice.repository.ContractRepository;
import ua.compservice.repository.CounterRepository;

@RestController
@RequestMapping("/contract")
public class ContractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractController.class.getSimpleName());
	
	@Autowired 
	private CounterRepository counterRepos;
	
	@Autowired
	private ContractRepository contractRepository;
	
	
	@GetMapping("list")
	public List<Contract> list() {
		return contractRepository.findAll();
	}
	
	@GetMapping("{number}")
	public Contract byNumber(@PathVariable("number") String number) {
		return this.contractRepository.findByNumber(number);
	}

	@GetMapping("id/{id}")
	public Contract byId(@PathVariable("id") Long id) {
		return this.contractRepository.findOne(id);
	}
	
	@GetMapping("byclient/{code}")
	public List<Contract> byClient(@PathVariable("code") String code) {
		
		return this.contractRepository.findByClientCode(code);

		
	}
	
	@GetMapping("byNumber/{number}")
	public Map<String, Object> byNumberStartingWith(@PathVariable("number") String number) {
		
		List<Contract> contracts = contractRepository.findByNumberStartingWith(number);
		
		logger.info("counters: {}", counterRepos.count());
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("contracts", contracts);
		return map;
	}
	
	
	
	
	
}
