package com.example.cinema.repository

import com.example.cinema.model.CinemaHall
import com.example.cinema.model.Movie
import com.example.cinema.model.MovieSession
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@DataMongoTest
class MovieSessionRepositoryTest {
    @Autowired
    lateinit var movieSessionRepository: MovieSessionRepository

    private val laLaLandMovie = Movie(title = "La la land", description = "Musical")
    private val cinemaHall1 = CinemaHall(description = "Hall 1", capacity = 20)
    private val cinemaHall2 = CinemaHall(description = "Hall 2", capacity = 30)
    private val showTime = LocalDateTime.of(2022, Month.JULY, 5, 20, 0,0)
    private val showTime1 = LocalDateTime.of(2022, Month.JULY, 5, 14, 0,0)
    private val movieSession = MovieSession(
        movieId = laLaLandMovie.id,
        cinemaHallId = cinemaHall1.id,
        showTime = showTime)
    private val movieSession2 = MovieSession(
        movieId = laLaLandMovie.id,
        cinemaHallId = cinemaHall2.id,
        showTime = showTime1)

    @BeforeEach
    internal fun setUp() {
        movieSessionRepository.deleteAll().block()
    }

    @Test
    fun shouldFindAvailableMovieSession() {
        movieSessionRepository.save(movieSession).block()
        StepVerifier
            .create(movieSessionRepository.findAvailableMovieSessions(Mono.just(laLaLandMovie),
                LocalDate.of(2022, Month.JULY, 5)))
            .expectNext(movieSession)
            .expectComplete()
            .verify()
    }

    @Test
    fun shouldReturnTwoMovieSessions() {
        movieSessionRepository.save(movieSession).block()
        movieSessionRepository.save(movieSession2).block()
        StepVerifier
            .create(movieSessionRepository.findAvailableMovieSessions(Mono.just(laLaLandMovie),
                LocalDate.of(2022, Month.JULY, 5)))
            .expectNext(movieSession, movieSession2)
            .expectComplete()
            .verify()
    }

    @Test
    fun shouldReturnNothingWithWrongDate() {
        movieSessionRepository.save(movieSession).block()
        StepVerifier
            .create(movieSessionRepository.findAvailableMovieSessions(Mono.just(laLaLandMovie),
                LocalDate.of(2022, Month.JULY, 6)))
            .expectComplete()
            .verify()
    }

    @Test
    fun shouldReturnNothingWithWrongMovie() {
        movieSessionRepository.save(movieSession).block()
        StepVerifier
            .create(movieSessionRepository
                .findAvailableMovieSessions(Mono.just(Movie(title = "", description = "")),
                    LocalDate.of(2022, Month.JULY, 5)))
            .expectComplete()
            .verify()
    }
}