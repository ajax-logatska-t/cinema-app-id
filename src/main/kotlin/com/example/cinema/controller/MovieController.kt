package com.example.cinema.controller

import com.example.cinema.model.Movie
import com.example.cinema.service.MovieService
import com.example.cinema.service.MovieStreamingServiceClient
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService,
    private val movieStreamingServiceClient: MovieStreamingServiceClient
) {
    @PostMapping fun add(@RequestBody movie: Movie) =
        movieService.save(movie)

    @GetMapping("/{id}") fun findById(@PathVariable id: String) =
        movieService.findById(id)

    @GetMapping fun getAll(): Flux<Movie> = movieService.findAll()

    @GetMapping("/generate") fun generate()
    = movieStreamingServiceClient.makeRequest()

    @PutMapping fun updateDescriptionByTitle(
        @RequestParam title: String,
        @RequestParam description: String) =
        movieService.updateDescriptionByTitle(title, description)

    @DeleteMapping("/{id}") fun deleteById(@PathVariable id: String) =
        movieService.deleteById(id)
}