package com.example.cinema.config

import com.mongodb.reactivestreams.client.MongoClient
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@Configuration
class ReactiveMongoConfig : AbstractReactiveMongoConfiguration() {
    @Bean
    override fun reactiveMongoClient(): MongoClient {
        return super.reactiveMongoClient()
    }

    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), "test")
    }

    @Bean
    fun channel(): ManagedChannel {
        return ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build()
    }

    override fun getDatabaseName() = "cinema2"
}