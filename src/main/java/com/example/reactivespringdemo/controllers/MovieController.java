package com.example.reactivespringdemo.controllers;

import com.example.reactivespringdemo.documents.Movie;
import com.example.reactivespringdemo.payloads.MovieEvent;
import com.example.reactivespringdemo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value ="/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> events(@PathVariable String id){
        return movieService.byId(id)
            .flatMapMany(movie -> movieService.streamStreams(movie));

    }

    @GetMapping
    public Flux<Movie> all(){
        return movieService.all();
    }

    @GetMapping("/{id}")
    public Mono<Movie> byId(@PathVariable String id){
        return movieService.byId(id);
    }
}
