package ua.compservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/words")
public class WordsController {
	
	private static Logger logger = LoggerFactory.getLogger(WordsController.class.getName());
	
	private static final String[] NAMES = new String[10];
	private static final int NUMBERS_BY_DEFAULT = 100;
	
	static {
		NAMES[0] = "Dmitriy";
		NAMES[1] = "Alex";
		NAMES[2] = "Vasiliy";
		NAMES[3] = "Vladik";
		NAMES[4] = "Orest";
		NAMES[5] = "Nikolas";
		NAMES[6] = "John";
		NAMES[7] = "Pamella";
		NAMES[8] = "Irina";
		NAMES[9] = "Bogdan";
		
	}
	
	
	@GetMapping("{number}")
	public List<String> words(@PathVariable("number") int number) {
		
		logger.info("Entered number is : {}", number);
		
		List<String> words = new ArrayList<>();
		
		Random randomizer = new Random();
		
		
		int actualNumber = number <= 0 ? NUMBERS_BY_DEFAULT : number;
		
		logger.info("Actual used the number: {}", actualNumber);
		
		for (int i = 0; i < actualNumber; i++) {
			words.add(NAMES[randomizer.nextInt(10)] + " " + String.valueOf(i));
		}
		
		return words;
		
	}

}
