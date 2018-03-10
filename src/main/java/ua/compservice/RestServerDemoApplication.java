package ua.compservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Pair;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.model.Client;
import ua.compservice.model.Contract;
import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;
import ua.compservice.model.ServiceType;
import ua.compservice.model.TradingPlace;
import ua.compservice.repository.ClientRepository;
import ua.compservice.repository.ContractRepository;
import ua.compservice.repository.CounterRepository;
import ua.compservice.repository.CounterValuesOnPeriodRepository;
import ua.compservice.repository.TradingPlaceRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.compservice", "ua.compservice.json.converter"})
public class RestServerDemoApplication implements ApplicationRunner {
	
	private static Logger logger = LoggerFactory.getLogger(RestServerDemoApplication.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private ContractRepository contractRepository;

	private TradingPlaceRepository tradingPlaceRepository;

	private ClientRepository clientRepository;

	private CounterRepository counterRepository;
	
	@Autowired
	private CounterValuesOnPeriodRepository counterValueRepository;
	
	@Autowired
	public RestServerDemoApplication(ContractRepository contractRepos, TradingPlaceRepository tpRepos, ClientRepository clientRepos, CounterRepository counterRepository) {
		this.contractRepository = contractRepos;
		this.tradingPlaceRepository = tpRepos;
		this.clientRepository = clientRepos;
		this.counterRepository = counterRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RestServerDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		//loadContractInfo();
		
		//initUserRoles();
		
		//loadCounterValues();
		
		
		
		
		
			
	}

	private void loadCounterValues()
			throws FileNotFoundException, IOException, JsonParseException, JsonMappingException {
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
						
						//System.out.println(cvOnPeriod);
						
					}
				}
				
			});
	}

	private void initUserRoles() {
		Arrays.asList(
				Pair.of("Dimkas", "Admin"),
				Pair.of("Vladi", "Admin"),
				Pair.of("Orest", "User")
				).stream()
					.forEach(p -> {
						
						Role r = roleRepository.findByName(p.getSecond());
						
						if (r == null) {
							r = roleRepository.save(new Role(null, p.getSecond()));
						}
						
						User u = userRepository.findByName(p.getFirst());
						
						if (u == null) {
							u = userRepository.save(new User(null, p.getFirst(), r));
						}
							
						
					});
		
		User u = userRepository.findOne(1l);
		
		System.out.println(u);
		
		userRepository.delete(u);
	}
	
	private void loadContractInfo() throws IOException {
		
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
	
}


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToOne 
	private Role role;
	
}


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
}

interface UserRepository extends JpaRepository<User, Long> {
	User findByName(String name);
	User findByRole(Role role);
}

interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}


