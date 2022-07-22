package com.example.cinema.service

import io.nats.client.Nats

fun main() {
    try {
        Nats.connect().run {
            print("Waiting for a message...")

            val dispatcher = this.createDispatcher()
            dispatcher.subscribe("subject") { println("Received: ${it.subject} ${String(it.data)}") }
        }
    } catch (exp: Exception) {
        exp.printStackTrace()
    }
    Thread.sleep(3000000)
}