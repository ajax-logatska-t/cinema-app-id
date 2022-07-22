package com.example.cinema.repository

import com.example.cinema.model.Ticket
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class TicketRepositoryTest {
    @Autowired
    lateinit var ticketRepository: TicketRepository

    @BeforeEach
    internal fun setUp() {
        ticketRepository.deleteAll().block()
    }

    @Test
    fun shouldReturnAllTicketsByUserId() {
        val ticket1 = ticketRepository.save(Ticket(userId = "1", movieSessionId = "1")).blockOptional().get()
        val ticket2 = ticketRepository.save(Ticket(userId = "1", movieSessionId = "2")).blockOptional().get()
        StepVerifier
            .create(ticketRepository.findAllTicketsByUserId("1"))
            .expectNext(ticket1, ticket2)
            .expectComplete()
            .verify()
    }
}