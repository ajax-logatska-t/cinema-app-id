package com.example.cinema.controller

import com.example.cinema.service.OrderService
import com.example.cinema.service.ShoppingCartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(private val shoppingCartService: ShoppingCartService,
                      private val orderService: OrderService
) {
    @PostMapping("/complete") fun completeOrder(@RequestParam userId: String) =
        orderService.completeOrder(shoppingCartService.findByUserId(userId))

    @GetMapping fun getOrderHistory(@RequestParam userId: String) =
        orderService.getOrderHistory(userId)
}
