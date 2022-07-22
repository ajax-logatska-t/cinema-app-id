package com.example.cinema.service

import com.example.cinema.repository.TicketRepository
import io.grpc.ManagedChannel
import org.springframework.stereotype.Service
import proto.cinema.ReactorTicketParserGrpc
import proto.cinema.StreamRequest
import proto.cinema.TicketRequest
import java.time.ZoneOffset

@Service
class TicketParserServiceClient(
    private val channel: ManagedChannel,
    private val movieSessionService: MovieSessionService,
    private val movieService: MovieService,
    private val cinemaHallService: CinemaHallService,
    private val userService: UserService,
    private val ticketRepository: TicketRepository
) {
    private val stub = ReactorTicketParserGrpc.newReactorStub(channel)

    fun makeRequest(ticketId: String) {
        ticketRepository.findById(ticketId).flatMap {
            val builder = TicketRequest.newBuilder()
            movieSessionService.findById(it.movieSessionId)
                .zipWith(userService.findById(it.userId))
                .flatMap { t ->
                cinemaHallService.findById(t.t1.cinemaHallId).map { ch ->
                    builder.cinemaHallDescription = ch.description
                }.flatMap {
                    movieService.findById(t.t1.movieId).map { m ->
                        builder.movieTitle = m.title
                    }
                }.map {
                    builder.showtime = t.t1.showTime.toEpochSecond(ZoneOffset.UTC)
                    builder.username = t.t2.email
                    builder.build()
                }.flatMap { tr ->
                    stub.parseTicket(tr)
                }
            }
        }.subscribe()
        /*ticketRepository.findById(ticketId).flatMap {
            movieSessionService.findById(it.movieSessionId).flatMap { ms ->
                movieService.findById(ms.movieId)
                    .zipWith(cinemaHallService.findById(ms.cinemaHallId))
                    .zipWith(Mono.just(ms.showTime))}
                    .zipWith(userService.findById(it.userId))
                    .flatMap { t -> stub.parseTicket(TicketRequest.newBuilder()
                        .setUsername(t.t2.email)
                        .setMovieTitle(t.t1.t1.t1.title)
                        .setCinemaHallDescription(t.t1.t1.t2.description)
                        .setShowtime(t.t1.t2.toEpochSecond(ZoneOffset.UTC))
                        .build())
                    }
        }.subscribe()*/
    }

    fun makeStreamRequest(cinemaHallId: String) {
        cinemaHallService.findById(cinemaHallId).map {
            stub.increment(
                StreamRequest.newBuilder()
                .setName(it.description)
                .setCount(it.capacity)
                .build())
        }.blockOptional().get().toIterable().forEach { println(it) }
    }
}
