package com.example.cinema.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Movie(
    @Id var id: String = ObjectId.get().toHexString(),
    var title: String,
    var description: String)

@Document
data class CinemaHall(
    @Id var id: String = ObjectId.get().toHexString(),
    var description: String,
    var capacity: Int)

@Document data class MovieSession(
    @Id var id: String = ObjectId.get().toHexString(),
    var movieId: String,
    var cinemaHallId: String,
    var showTime: LocalDateTime)

@Document data class Order(
    @Id var id: String = ObjectId.get().toHexString(),
    var ticketIds: List<String>,
    var orderTime: LocalDateTime,
    var userId: String)

@Document data class ShoppingCart(
    @Id var id: String = ObjectId.get().toHexString(),
    var userId: String,
    var ticketIds: List<String>,
    @Version val version: Long = 0L)

@Document data class Ticket (
    @Id var id: String = ObjectId.get().toHexString(),
    var userId: String,
    var movieSessionId: String)

@Document data class User (
    @Id var id: String = ObjectId.get().toHexString(),
    var email: String,
    var password: String,
    var roles: Set<Role>)

@Document data class Role (var roleName: RoleName)

enum class RoleName { ADMIN, USER }