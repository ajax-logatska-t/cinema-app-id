package com.example.cinema.controller

import com.example.cinema.model.CinemaHall
import com.example.cinema.service.CinemaHallService
import com.example.cinema.service.TicketParserServiceClient
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/cinema-halls")
class CinemaHallController(
    private val cinemaHallService: CinemaHallService,
    private val ticketParserServiceClient: TicketParserServiceClient
) {
    @PostMapping fun add(@RequestBody cinemaHall: CinemaHall) =
        cinemaHallService.save(cinemaHall)

    @GetMapping("/{id}") fun findById(@PathVariable id: String) =
        cinemaHallService.findById(id)

    @GetMapping fun getAll(): Flux<CinemaHall> =
        cinemaHallService.findAll()

    @GetMapping("/increment/{id}") fun incrementCapacity(
        @PathVariable id: String) =
        ticketParserServiceClient.makeStreamRequest(id)

    @PutMapping fun updateCapacityByDescription(
        @RequestParam description: String,
        @RequestParam capacity: Int
    ) = cinemaHallService.updateCapacityByDescription(description, capacity)

    @DeleteMapping("/{id}") fun deleteById(@PathVariable id: String) =
        cinemaHallService.deleteByID(id)
}