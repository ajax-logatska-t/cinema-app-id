package com.example.cinema.repository

import com.example.cinema.model.*
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface MyCustomRepository {
    fun insertMovie(movie: Movie): Mono<Movie>

    fun updateDescriptionByTitle(title: String, description: String): Mono<Movie>

    fun updateCapacityByDescription(description: String, capacity: Int): Mono<CinemaHall>

    fun findAvailableMovieSessions(movie: Mono<Movie>, date: LocalDate): Flux<MovieSession>

    fun addTicketsToCart(shoppingCart: Mono<ShoppingCart>,
                         movieSession: Mono<MovieSession>): Mono<ShoppingCart>

    fun deleteCartByUserId(userId: String): Mono<ShoppingCart>

    fun findAllTicketsByUserId(userId: String): Flux<Ticket>

    fun findAllOrdersByUserId(userId: String): Flux<Order>
}

class MyCustomRepositoryImpl(private val template: ReactiveMongoTemplate)
    : MyCustomRepository {

    override fun insertMovie(movie: Movie) = template.insert(movie)

    override fun updateDescriptionByTitle(title: String, description: String): Mono<Movie> {
        val query = query(Criteria.where("title").`is`(title))
        val update = Update().set("description", description)
        return template
            .findAndModify(query, update, Movie::class.java)
    }

    override fun updateCapacityByDescription(description: String, capacity: Int): Mono<CinemaHall> {
        val query = query(Criteria.where("description").`is`(description))
        val update = Update().inc("capacity", capacity)
        return template.findAndModify(query, update, CinemaHall::class.java)
    }

    override fun findAvailableMovieSessions(movie: Mono<Movie>, date: LocalDate): Flux<MovieSession> {
        val from = date.atStartOfDay()
        val to = date.atTime(23, 59)
        return movie.flatMapMany {
            val query = query(Criteria.where("movieId").`is`(it.id)
                .and("showTime").gte(from).lte(to))
            template
                .find(query, MovieSession::class.java)
        }
    }

    override fun addTicketsToCart(shoppingCart: Mono<ShoppingCart>,
                                  movieSession: Mono<MovieSession>): Mono<ShoppingCart> {
        return shoppingCart.zipWith(movieSession).flatMap {
            val sCart = it.t1
            val mSession = it.t2
            template.insert(Ticket(userId = sCart.userId, movieSessionId = mSession.id)).flatMap {
                val query = query(Criteria.where("userId").`is`(sCart.userId))
                val update = Update().push("ticketIds", Ticket(userId = sCart.userId, movieSessionId = mSession.id).id)
                template.findAndModify(query, update, ShoppingCart::class.java)
            }
        }
    }

    override fun deleteCartByUserId(userId: String): Mono<ShoppingCart> {
        val query = query(Criteria.where("userId").`is`(userId))
        return template.findAndRemove(query, ShoppingCart::class.java)
    }

    override fun findAllTicketsByUserId(userId: String): Flux<Ticket> {
        val query = query(Criteria.where("userId").`is`(userId))
        return template.find(query, Ticket::class.java)
    }
    override fun findAllOrdersByUserId(userId: String): Flux<Order> {
        val query = query(Criteria.where("userId").`is`(userId))
        return template.find(query, Order::class.java)
    }
}