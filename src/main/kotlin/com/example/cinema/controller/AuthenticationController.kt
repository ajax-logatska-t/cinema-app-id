package com.example.cinema.controller

import com.example.cinema.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(private val authService: AuthenticationService) {
    @PostMapping("/register")
    fun register(@RequestParam email: String, @RequestParam password: String) =
        authService.register(email, password)
}