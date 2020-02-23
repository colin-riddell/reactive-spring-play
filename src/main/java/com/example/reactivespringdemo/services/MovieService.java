package com.example.reactivespringdemo.services;

import com.example.reactivespringdemo.documents.Movie;
import com.example.reactivespringdemo.payloads.MovieEvent;
import com.example.reactivespringdemo.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieService {


    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<MovieEvent> streamStreams(Movie movie){
        // A flux of Longs that's going to pub a new value every one sec
        // as a scheduler
        // The intervail is just to slow down the stream. Without it, it's hard to stop
        // using ctrl+c when using curl.
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));


        Flux<MovieEvent> events = Flux.fromStream(Stream.generate(()->
            new MovieEvent(movie, new Date(), "me")
        ));
        //return events;
        return Flux.zip(interval, events).map(Tuple2::getT2);

    }

    public Flux<Movie> all(){
        return movieRepository.findAll();
    }

    public Mono<Movie> byId(String id){
        return movieRepository.findById(id);
    }
}
