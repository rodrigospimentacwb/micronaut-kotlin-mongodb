package com.example.request

import io.micronaut.serde.annotation.Serdeable.Deserializable
import io.micronaut.serde.annotation.Serdeable.Serializable

@Serializable
@Deserializable
data class SearchRequest(
    val name:String? = null,
    val email: String? = null
)
