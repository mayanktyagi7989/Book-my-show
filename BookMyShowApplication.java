package dev.aman.bookmyshow;

import dev.aman.bookmyshow.Controllers.UserController;
import dev.aman.bookmyshow.DTOs.SignUpRequestDTO;
import dev.aman.bookmyshow.DTOs.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // JPA will keep the check in the database for changes
public class BookMyShowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDTO requestDTO  = new SignUpRequestDTO();
		requestDTO.setName("Aman");
		requestDTO.setEmail("aman1@gmail.com");
		requestDTO.setPassword("1234");

		SignUpResponseDTO signUpResponseDTO = userController.signUp(requestDTO);

	}
}
