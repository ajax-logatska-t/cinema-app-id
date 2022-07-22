package com.example.cinema.repository

import com.example.cinema.model.Movie
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class MovieRepositoryTest {
    @Autowired
    lateinit var movieRepository: MovieRepository

    private val laLaLandMovie = Movie(title = "La la land", description = "Musical")
    private val prideAndPrejudiceMovie = Movie(title = "PrideAndPrejudice", description = "British Classics")

    @BeforeEach
    internal fun tearDown() {
        movieRepository.deleteAll().block()
    }

    @Test
    internal fun shouldInsertMovie() {
        val expected = movieRepository
            .insertMovie(laLaLandMovie)
            .blockOptional().get()
        StepVerifier
            .create(movieRepository.findById(laLaLandMovie.id))
            .expectNext(expected)
            .expectComplete()
            .verify()
    }

    @Test
    internal fun shouldUpdateDescriptionByTitle() {
        movieRepository.save(prideAndPrejudiceMovie).block()
        val update = movieRepository
            .updateDescriptionByTitle(title = "PrideAndPrejudice", description = "British Romance")
        StepVerifier
            .create(update.map { it.description })
            .expectNext("British Classics")
            .expectComplete()
            .verify()
        StepVerifier.create(movieRepository.findByTitle("PrideAndPrejudice").map { it.description })
            .expectNext("British Romance")
            .expectComplete()
            .verify()
    }
}