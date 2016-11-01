package org.jasonxiao;

import org.jasonxiao.model.User;
import org.jasonxiao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class Application {

	@Autowired UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		userRepository.save(new User("jason", "password", User.Role.ADMIN));
	}
}
