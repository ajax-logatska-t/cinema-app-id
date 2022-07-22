package com.example.cinema.service

import com.example.cinema.model.CinemaHall
import com.example.cinema.repository.CinemaHallRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CinemaHallService {
    fun save(cinemaHall: CinemaHall): Mono<CinemaHall>

    fun findById(id: String): Mono<CinemaHall>

    fun findByDescription(description: String): Mono<CinemaHall>

    fun findAll(): Flux<CinemaHall>

    fun updateCapacityByDescription(description: String, capacity: Int): Mono<CinemaHall>

    fun deleteByID(id: String): Mono<Void>
}

@Service
class CinemaHallServiceImpl(private val cinemaHallRepository: CinemaHallRepository)
    : CinemaHallService {
    override fun save(cinemaHall: CinemaHall) = cinemaHallRepository.save(cinemaHall)

    override fun findById(id: String) = cinemaHallRepository.findById(id)

    override fun findByDescription(description: String) =
        cinemaHallRepository.findByDescription(description)

    override fun findAll() = cinemaHallRepository.findAll()

    override fun updateCapacityByDescription(description: String, capacity: Int) =
        cinemaHallRepository.updateCapacityByDescription(description, capacity)

    override fun deleteByID(id: String) = cinemaHallRepository.deleteById(id)
}