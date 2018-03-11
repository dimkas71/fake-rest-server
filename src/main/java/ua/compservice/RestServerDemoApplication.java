package ua.compservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.compservice", "ua.compservice.json.converter"})
public class RestServerDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestServerDemoApplication.class, args);
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


