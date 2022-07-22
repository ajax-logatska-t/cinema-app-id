package com.example.cinema.service

import io.grpc.ManagedChannel
import org.springframework.stereotype.Service
import proto.cinema.MovieRequest
import proto.cinema.ReactorMovieStreamingGrpc

@Service
class MovieStreamingServiceClient(
    private val channel: ManagedChannel,
    private val movieService: MovieService
) {
    private val stub = ReactorMovieStreamingGrpc.newReactorStub(channel)

    fun makeRequest() {
        stub.streamMovie(
            MovieRequest
                .newBuilder()
                .addAllTitle(movieService.findAll().map { it.title }.toIterable())
                .build()
        ).subscribe { println(it) }
    }
}