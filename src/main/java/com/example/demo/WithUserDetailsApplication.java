package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class WithUserDetailsApplication {

	@RepositoryRestController
	@RequestMapping("/api")
	@Configuration
	static class MyController {
		@PostMapping("/resources/{id}/attributes")
		public @ResponseBody
		ResponseEntity<?> attributes(
				@PathVariable("id") long resourceId,
				@RequestParam(value = "file", required = true) MultipartFile file,
				Authentication authentication) throws IOException {
			return ResponseEntity.ok(authentication.getName());
		}
	}

	@EnableWebSecurity
	static class Config extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public UserDetailsService userDetailsService() {
			return new InMemoryUserDetailsManager(
					User.withDefaultPasswordEncoder()
							.username("bob")
							.password("bobby")
							.roles("USER")
							.build());
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(WithUserDetailsApplication.class, args);
	}

}

