package com.example.cinema.service

import com.example.cinema.model.MovieSession
import com.example.cinema.model.ShoppingCart
import com.example.cinema.model.User
import com.example.cinema.repository.ShoppingCartRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface ShoppingCartService {
    fun addSession(movieSession: Mono<MovieSession>, userId: String): Mono<ShoppingCart>

    fun findByUserId(userId: String): Mono<ShoppingCart>

    fun registerNewShoppingCart(user: Mono<User>): Mono<ShoppingCart>

    fun deleteByUserId(userId: String): Mono<ShoppingCart>
}

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepository: ShoppingCartRepository
    ): ShoppingCartService {
    override fun addSession(movieSession: Mono<MovieSession>, userId: String): Mono<ShoppingCart> =
        shoppingCartRepository
            .addTicketsToCart(shoppingCartRepository.findByUserId(userId), movieSession)

    override fun findByUserId(userId: String) = shoppingCartRepository.findByUserId(userId)

    override fun registerNewShoppingCart(user: Mono<User>) =
        user.flatMap { shoppingCartRepository.save(ShoppingCart(userId = it.id, ticketIds = emptyList())) }

    override fun deleteByUserId(userId: String) =
        shoppingCartRepository.deleteCartByUserId(userId)
}