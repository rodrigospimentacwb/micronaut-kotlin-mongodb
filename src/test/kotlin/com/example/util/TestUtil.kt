package com.example.util

import io.restassured.common.mapper.TypeRef
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification

fun RequestSpecification.whenever(): RequestSpecification =
    this.`when`()

fun <T> ValidatableResponse.extractAs(typeRef: TypeRef<T>) =
    this.extract()
        .`as`(typeRef)

fun <T> ValidatableResponse.extractAs(clazz: Class<T>) =
    this.extract()
        .`as`(clazz)
