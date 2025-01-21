package uni.mainz.TrainingsTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@GetMapping("")
	public String welcomePage() {
		return "Welcome to the Trainings Tracker!";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// Basic Auth for every Api Access
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers("/", "hello").permitAll()
								.requestMatchers("/api/**").authenticated())
				.httpBasic(withDefaults());
		return http.build();
	}
}
