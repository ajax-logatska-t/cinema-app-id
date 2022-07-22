package com.example.cinema.repository

import com.example.cinema.model.Order
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier
import java.time.LocalDateTime
import java.time.Month

@DataMongoTest
class OrderRepositoryTest {
    @Autowired
    lateinit var orderRepository: OrderRepository

    @BeforeEach
    internal fun setUp() {
        orderRepository.deleteAll().block()
    }

    @Test
    fun shouldFindAllOrdersByUserId() {
        val order1 = orderRepository.save(Order(
            ticketIds = listOf("1"),
            orderTime = LocalDateTime.of(2022, Month.JULY, 5, 20, 0),
            userId = "1"
        )).blockOptional().get()
        val order2 = orderRepository.save(Order(
            ticketIds = listOf("2"),
            orderTime = LocalDateTime.of(2022, Month.JULY, 4, 20, 0),
            userId = "1"
        )).blockOptional().get()
        StepVerifier
            .create(orderRepository.findAllOrdersByUserId("1"))
            .expectNext(order1, order2)
            .expectComplete()
            .verify()
    }
}