package com.example.cinema.repository

import com.example.cinema.model.CinemaHall
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class CinemaHallRepositoryTest {
    @Autowired
    lateinit var cinemaHallRepository: CinemaHallRepository

    private val cinemaHall1 = CinemaHall(description = "Hall 1", capacity = 30)

    @BeforeEach
    internal fun setUp() {
        cinemaHallRepository.deleteAll().block()
    }

    @Test
    fun shouldIncreaseCapacityBy10() {
        cinemaHallRepository.save(cinemaHall1).block()
        val update = cinemaHallRepository.updateCapacityByDescription("Hall 1", 10)
        StepVerifier
            .create(update.map { it.capacity })
            .expectNext(30)
            .expectComplete()
            .verify()
        StepVerifier
            .create(cinemaHallRepository
                .findByDescription("Hall 1").map { it.capacity })
            .expectNext(40)
            .expectComplete()
            .verify()
    }
}