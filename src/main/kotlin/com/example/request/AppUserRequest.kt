package com.example.request

import io.micronaut.serde.annotation.Serdeable.Deserializable
import io.micronaut.serde.annotation.Serdeable.Serializable

@Serializable
@Deserializable
data class AppUserRequest(
    val name: String,
    val email: String,
    val street: String,
    val city: String,
    val code: Int
)