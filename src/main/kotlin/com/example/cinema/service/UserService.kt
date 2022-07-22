package com.example.cinema.service

import com.example.cinema.model.Ticket
import com.example.cinema.model.User
import com.example.cinema.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun save(user: User): Mono<User>

    fun findById(id: String): Mono<User>

    fun findByEmail(email: String): Mono<User>

    fun findAll(): Flux<User>

    fun findAllTicketsByUserId(userId: String): Flux<Ticket>
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository): UserService {
    override fun save(user: User) = userRepository.save(user)

    override fun findById(id: String) = userRepository.findById(id)

    override fun findByEmail(email: String) = userRepository.findByEmail(email)

    override fun findAll() = userRepository.findAll()

    override fun findAllTicketsByUserId(userId: String) =
        userRepository.findAllTicketsByUserId(userId)
}