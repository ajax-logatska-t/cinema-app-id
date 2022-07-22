package com.example.cinema.repository

import com.example.cinema.model.MovieSession
import com.example.cinema.model.ShoppingCart
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@DataMongoTest
class ShoppingCartRepositoryTest {
    @Autowired
    lateinit var shoppingCartRepository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        shoppingCartRepository.deleteAll().block()
    }


    @Test
    fun shouldAddTicketToCart() {
        val movieSession = Mono.just(MovieSession(movieId = "1", cinemaHallId = "2", showTime = LocalDateTime.now()))
        val shoppingCart = shoppingCartRepository.save(ShoppingCart(userId = "1", ticketIds = emptyList()))
        val update = shoppingCartRepository.addTicketsToCart(shoppingCart, movieSession)
        StepVerifier.create(update.map { it.ticketIds.size })
            .expectNext(0)
            .expectComplete()
            .verify()
        StepVerifier.create(shoppingCartRepository.findByUserId("1").map { it.ticketIds.size })
            .expectNext(1)
            .expectComplete()
            .verify()
    }

    @Test
    fun shouldDeleteCartByUserId() {
        shoppingCartRepository.save(ShoppingCart(userId = "1", ticketIds = emptyList())).block()
        val delete = shoppingCartRepository.deleteCartByUserId("1")
        StepVerifier
            .create(delete.map { it.userId })
            .expectNext("1")
            .expectComplete()
            .verify()
        StepVerifier
            .create(shoppingCartRepository.findByUserId("1"))
            .expectComplete()
            .verify()
    }
}