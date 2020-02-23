package com.example.reactivespringdemo;

import com.example.reactivespringdemo.documents.Movie;
import com.example.reactivespringdemo.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveSpringDemoApplication {

	@Bean
	CommandLineRunner demo(MovieRepository movieRepository){
		return args -> {
			movieRepository.deleteAll()
				.subscribe(null, null,()->{
					Stream.of("Aeon Flux","Enter the MonoVoid", "The Fluxinator",
						"Silence of the lambdas", "Goodfellas", "Attack of the Fluxes",
						"All your base", "Back to the Future")
						.map( name -> new Movie(UUID.randomUUID().toString(), name, randomGenre()))
						.forEach(m -> movieRepository.save(m).subscribe(System.out::println));

				});
		};
	}

	private String randomGenre(){
		String [] genres = "horror,action,romcom,documentary".split(",");
		return genres[new Random().nextInt(genres.length)];
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringDemoApplication.class, args);
	}

}
