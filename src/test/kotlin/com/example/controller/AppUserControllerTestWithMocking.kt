package com.example.controller

import com.example.model.Address
import com.example.model.AppUser
import com.example.repository.AppUserRepository
import com.example.util.extractAs
import com.example.util.whenever
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class AppUserControllerTestWithMocking {

    private val repo: AppUserRepository = mockk<AppUserRepository>()

    @MockBean(AppUserRepository::class)
    fun appUserRepo(): AppUserRepository = repo

    @Test
    fun `should return 200 OK in GET users`(spec: RequestSpecification) {
        every {
            repo.findAll()
        } returns emptyList()

        spec
            .whenever()
            .get("/users")
            .then()
            .statusCode(200)
            .header("Content-Type", "application/json")
    }

    @Test
    fun `should return user by ID`(spec: RequestSpecification) {
        val founderUser = AppUser(
            id = "123",
            name = "John Doe",
            email = "john.doe@example.com",
            address = Address(
                street = "123 Elm Street",
                city = "Springfield",
                code = 12345
            )
        )

        every {
            repo.findById("123")
        } returns Optional.of(founderUser)

        spec
            .whenever()
            .get("/users/123")
            .then()
            .statusCode(200)
            .body("id", equalTo("123"))
            .body("address.street", equalTo("123 Elm Street"))
    }

    @Test
    fun `should return user by ID using extract`(spec: RequestSpecification) {
        val founderUser = AppUser(
            id = "123",
            name = "John Doe",
            email = "john.doe@example.com",
            address = Address(
                street = "123 Elm Street",
                city = "Springfield",
                code = 12345
            )
        )

        every {
            repo.findById("123")
        } returns Optional.of(founderUser)

        val user = spec
            .whenever()
            .get("/users/123")
            .then()
            .statusCode(200)
            .body("id", equalTo("123"))
            .body("address.street", equalTo("123 Elm Street"))
            .extractAs(AppUser::class.java)

        assertEquals(founderUser, user)
    }
}