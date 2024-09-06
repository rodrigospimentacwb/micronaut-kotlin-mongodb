package com.example.controller

import com.example.model.Address
import com.example.model.AppUser
import com.example.request.AppUserRequest
import com.example.request.SearchRequest
import com.example.service.AppUserService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Controller("/users")
class AppUserController(
    private val appUserService: AppUserService
) {

    @Post
    @Status(HttpStatus.CREATED)
    fun create(@Body appUserRequest: AppUserRequest) =
        appUserService.create(
            appUser = appUserRequest.toModel()
        )

    @Get
    fun getAll() =
        appUserService.findAll()

    @Get("/{id}")
    fun getById(@PathVariable id: String) =
        appUserService.getById(id)

    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: AppUserRequest,
        @Header("X-Foo") header: String): AppUser {
        println("Header: $header")
        return appUserService.update(id, request.toModel())
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        appUserService.delete(id)
    }

    @Post("/search")
    fun search(@Body searchRequest: SearchRequest): List<AppUser> =
        appUserService.search(searchRequest)

    private fun AppUserRequest.toModel(): AppUser =
        AppUser(
            name = this.name,
            email = this.email,
            address = Address(
                street = this.street,
                city = this.city,
                code = this.code
            )
        )
}