package com.example.cinema.controller

import com.example.cinema.service.MovieSessionService
import com.example.cinema.service.ShoppingCartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/shopping-carts")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService,
    private val movieSessionService: MovieSessionService
) {
    @PutMapping("/movie-sessions")
    fun addToCart(@RequestParam movieSessionId: String,
                  @RequestParam userId: String) {
        shoppingCartService.addSession(
            movieSessionService.findById(movieSessionId),
            userId).block()
    }

    @GetMapping("/by-user") fun getByUser(@RequestParam userId: String) =
        shoppingCartService.findByUserId(userId)

    @DeleteMapping fun delete(@RequestParam userId: String) =
        shoppingCartService.deleteByUserId(userId)
}