package ua.compservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.compservice.model.Contract;
import ua.compservice.repository.ContractRepository;

@RestController
@RequestMapping("/contract")
public class ContractController {
	
	
	private ContractRepository contractRepository;
	
	@Autowired
	public ContractController(ContractRepository repos) {
		this.contractRepository = repos;
	}
	
	@GetMapping("list")
	public List<Contract> list() {
		return contractRepository.findAll();
	}
	
	@GetMapping("{numer}")
	public Contract byNumber(@PathVariable("number") String number) {
		return this.contractRepository.findByNumber(number);
	}

}
