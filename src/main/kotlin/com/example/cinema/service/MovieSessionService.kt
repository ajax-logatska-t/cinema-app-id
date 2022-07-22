package com.example.cinema.service

import com.example.cinema.model.MovieSession
import com.example.cinema.repository.MovieSessionRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface MovieSessionService {
    fun save(movieSession: MovieSession): Mono<MovieSession>

    fun findById(id: String): Mono<MovieSession>

    fun findAll(): Flux<MovieSession>

    fun findAvailableMovieSessions(movieId: String, date: LocalDate): Flux<MovieSession>
}

@Service
class MovieSessionServiceImpl(
    private val movieSessionRepository: MovieSessionRepository,
    private val movieService: MovieService
    ): MovieSessionService {
    override fun save(movieSession: MovieSession) = movieSessionRepository.save(movieSession)

    override fun findById(id: String) = movieSessionRepository.findById(id)

    override fun findAll() = movieSessionRepository.findAll()

    override fun findAvailableMovieSessions(movieId: String, date: LocalDate) =
        movieSessionRepository.findAvailableMovieSessions(movieService.findById(movieId), date)
}