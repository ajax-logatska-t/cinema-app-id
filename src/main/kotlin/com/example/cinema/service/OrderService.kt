package com.example.cinema.service

import com.example.cinema.model.Order
import com.example.cinema.model.ShoppingCart
import com.example.cinema.repository.OrderRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface OrderService {
    fun completeOrder(shoppingCart: Mono<ShoppingCart>): Mono<Order>

    fun getOrderHistory(userId: String): Flux<Order>
}

@Service
class OrderServiceImpl(private val shoppingCartService: ShoppingCartService,
                       private val orderRepository: OrderRepository
): OrderService {
    override fun completeOrder(shoppingCart: Mono<ShoppingCart>): Mono<Order> {
        return shoppingCart.flatMap { sCart ->
            orderRepository.save(
                Order(
                    ticketIds = sCart.ticketIds,
                    orderTime = LocalDateTime.now(),
                    userId = sCart.userId
                )
            ).flatMap { order -> shoppingCartService.deleteByUserId(sCart.userId).map { order } }
        }
    }

    override fun getOrderHistory(userId: String) =
        orderRepository.findAllOrdersByUserId(userId)
}
