package ua.compservice;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.compservice.model.Counter;
import ua.compservice.model.CounterValuesOnPeriod;
import ua.compservice.repository.CounterRepository;
import ua.compservice.repository.CounterValuesOnPeriodRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.compservice", "ua.compservice.json.converter"})
public class RestServerDemoApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestServerDemoApplication.class);
	
	@Autowired
	private CounterValuesOnPeriodRepository repository;
	
	@Autowired
	private CounterRepository counterRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RestServerDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Command line runner: {}", repository.count());
		
		
		final Long counterId = 4848L;
		
		Counter counter = counterRepository.findOne(counterId);
		LOGGER.info("Counter by id {}, is {}", counterId, counter);
		
		LocalDate to = LocalDate.now();
		
		LocalDate from = to.minusYears(1);
		
		
		LOGGER.info("From: {} - To: {}", from, to);
		
		List<CounterValuesOnPeriod> values = repository.findByPeriodBetweenAndCounter(from, to, counter);
		
		
		values.stream()
			.sorted(Comparator.comparing(CounterValuesOnPeriod::getPeriod))
			.forEach(System.out::println);
		
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


