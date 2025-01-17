package uni.mainz.TrainingsTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TrainingsTrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainingsTrackerApplication.class, args);
	}

	@GetMapping("")
	public String welcomePage() {
		return "Welcome to the Trainings Tracker!";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
