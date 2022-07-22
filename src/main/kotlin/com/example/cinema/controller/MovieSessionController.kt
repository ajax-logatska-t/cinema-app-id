package com.example.cinema.controller

import com.example.cinema.model.MovieSession
import com.example.cinema.service.MovieSessionService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import com.example.cinema.util.DateTimePatternUtil
import java.time.LocalDate

@RestController
@RequestMapping("/movie-sessions")
class MovieSessionController(private val movieSessionService: MovieSessionService) {
    @PostMapping fun add(@RequestBody movieSession: MovieSession) =
        movieSessionService.save(movieSession)

    @GetMapping fun getAll() = movieSessionService.findAll()

    @GetMapping("/available") fun getAllAvailable(
        @RequestParam movieId: String,
        @RequestParam @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN) date: LocalDate
    ) = movieSessionService.findAvailableMovieSessions(movieId, date)
}