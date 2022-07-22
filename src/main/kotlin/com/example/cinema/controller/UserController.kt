package com.example.cinema.controller

import com.example.cinema.service.TicketParserServiceClient
import com.example.cinema.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService,
                     private val ticketParserServiceClient: TicketParserServiceClient
) {
    @GetMapping("/by-email") fun findByEmail(@RequestParam email: String) =
        userService.findByEmail(email)

    @GetMapping fun findAll() = userService.findAll()

    @GetMapping("/tickets") fun findAllTicketsByUserId(@RequestParam userId: String) =
        userService.findAllTicketsByUserId(userId)

    @GetMapping("/{id}")
    fun parseTicket(@PathVariable id: String) {
        ticketParserServiceClient.makeRequest(id)
    }
}