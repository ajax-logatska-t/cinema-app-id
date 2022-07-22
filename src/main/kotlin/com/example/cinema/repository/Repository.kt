package com.example.cinema.repository

import com.example.cinema.model.*
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface MovieRepository: ReactiveMongoRepository<Movie, String>, MyCustomRepository {
    fun findByTitle(title: String): Mono<Movie>
}

interface CinemaHallRepository: ReactiveMongoRepository<CinemaHall, String>, MyCustomRepository {
    fun findByDescription(description: String): Mono<CinemaHall>
}

interface MovieSessionRepository: ReactiveMongoRepository<MovieSession, String>, MyCustomRepository

interface ShoppingCartRepository: ReactiveMongoRepository<ShoppingCart, String>, MyCustomRepository {
    fun findByUserId(id: String): Mono<ShoppingCart>
}

interface OrderRepository: ReactiveMongoRepository<Order, String>, MyCustomRepository

interface TicketRepository: ReactiveMongoRepository<Ticket, String>, MyCustomRepository

interface UserRepository: ReactiveMongoRepository<User, String>, MyCustomRepository {
    fun findByEmail(email: String): Mono<User>
}

interface RoleRepository: ReactiveMongoRepository<Role, String> {
    fun findByRoleName(name: RoleName): Mono<Role>
}