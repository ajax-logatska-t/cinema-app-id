package com.example.cinema.service

import com.example.cinema.model.Role
import com.example.cinema.model.RoleName
import com.example.cinema.model.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

interface AuthenticationService {
    fun register(email: String, password: String): Mono<User>
}

@Service
class AuthenticationServiceImpl(private val userService: UserService,
                                private val shoppingCartService: ShoppingCartService
): AuthenticationService {
    override fun register(email: String, password: String): Mono<User> {
        return userService.save(User(
            email = email,
            password = password,
            roles = setOf(Role(RoleName.USER))
        )).flatMap { usr ->
            shoppingCartService.registerNewShoppingCart(usr.toMono()).map { usr }
        }
    }
}