package com.example.controller

import com.example.model.AppUser
import com.example.request.AppUserRequest
import com.example.util.extractAs
import com.example.util.whenever
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
@Requires(env = ["integration-test"])
class AppUserControllerWithoutMocking {

    @Inject
    private lateinit var context: ApplicationContext

    @Inject
    private lateinit var server: EmbeddedServer

    @Test
    fun `should return 200 OK in GET users`(spec: RequestSpecification) {
        spec
            .whenever()
            .get("/users")
            .then()
            .statusCode(200)
            .header("Content-Type", "application/json")
    }

    @Test
    fun `should return 404 NOT FOUND in GET user by id`(spec: RequestSpecification) {
        spec
            .whenever()
            .get("/users/123123123123123123123123")
            .then()
            .statusCode(404)
            .header("Content-Type", "application/json")
    }

    @Test
    fun `should creat a user`(spec: RequestSpecification) {
        val request = AppUserRequest(
            name = "John Doe",
            email = "john.doe@example.com",
            street = "123 Elm Street",
            city = "Springfield",
            code = 12345
        )

        spec
            .whenever()
            .contentType(ContentType.JSON)
            .body(request)
            .post("/users")
            .then()
            .statusCode(201)

        val list = spec
            .whenever()
            .get("/users")
            .then()
            .statusCode(200)
            .body("size()", `is`(1))
            .extractAs(object: TypeRef<List<AppUser>>(){})

        assertEquals(1 , list.size)

        val createdUser = list.first()
        assertEquals(request.name, createdUser.name)
        assertEquals(request.street, createdUser.address.street)
    }

}