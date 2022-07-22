package com.example.cinema.service

import com.example.cinema.model.Movie
import com.example.cinema.repository.MovieRepository
import io.nats.client.Nats
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MovieService {
    fun save(movie: Movie): Mono<Movie>

    fun findById(id: String): Mono<Movie>

    fun deleteById(id: String): Mono<Void>

    fun updateDescriptionByTitle(title: String, description: String): Mono<Movie>

    fun findByTitle(title: String): Mono<Movie>

    fun findAll(): Flux<Movie>
}

@Service
class MovieServiceImpl(
    private val movieRepository: MovieRepository
): MovieService {
    override fun save(movie: Movie): Mono<Movie> {
        val nc = Nats.connect()
        nc.publish("movies", movie.title.toByteArray())
        return movieRepository.insertMovie(movie)
    }

    override fun findById(id: String) = movieRepository.findById(id)

    override fun deleteById(id: String) = movieRepository.deleteById(id)

    override fun updateDescriptionByTitle(title: String, description: String) =
        movieRepository.updateDescriptionByTitle(title, description)

    override fun findByTitle(title: String) = movieRepository.findByTitle(title)

    override fun findAll() = movieRepository.findAll()
}