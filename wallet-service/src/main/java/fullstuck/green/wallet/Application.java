package fullstuck.green.wallet;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Strings.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@SpringBootApplication
public class Application {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountDetailsRepository accountDetailsRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
//			Role role = roleService.getOrSave(RoleEnum.ROLE_ADMIN);
//
//			User user = User.builder()
//					.merchant(null)
//					.name("Wallet Admin")
//					.birthDate(Date.from(Instant.now()))
//					.phone(String.valueOf((Math.random()*913013012)))
//					.created_at(Date.from(Instant.now()))
//					.updated_at(Date.from(Instant.now()))
//					.isDeleted(Boolean.FALSE)
//					.build();
//			userRepository.save(user);
//
//			AccountDetails accountDetails = AccountDetails.builder()
//					.email("admin@grn.wallet.com")
//					.password(passwordEncoder.encode("PasswordForLogin"))
//					.balance(new BigDecimal("0"))
//					.point(null)
//					.role(role)
//					.user(user)
//					.balance(new BigDecimal("0.0"))
//					.created_at(Date.from(Instant.now()))
//					.updated_at(Date.from(Instant.now()))
//					.isDeleted(Boolean.FALSE)
//					.isVerified(Boolean.TRUE)
//					.build();
//			accountDetailsRepository.save(accountDetails);
		};
	}
}
