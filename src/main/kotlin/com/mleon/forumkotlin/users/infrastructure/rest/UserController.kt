package com.mleon.forumkotlin.users.infrastructure.rest

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.IdentifierDtoOutput
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.infrastructure.controller.EmptyResponse
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsUsersProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.UserProjection
import com.mleon.forumkotlin.users.application.dto.input.UpdateUserDto
import com.mleon.forumkotlin.users.application.dto.input.UserDto
import com.mleon.forumkotlin.users.application.useCases.UserCreator
import com.mleon.forumkotlin.users.application.useCases.UserRemover
import com.mleon.forumkotlin.users.application.useCases.UserUpdater
import com.mleon.forumkotlin.users.infrastructure.rest.dto.input.UpdateUserDtoValidator
import com.mleon.forumkotlin.users.infrastructure.rest.dto.input.UserDtoValidator
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("users")
@RestController
@Validated
class UserController(
    private val userCreator: UserCreator,
    private val userUpdater: UserUpdater,
    private val userRemover: UserRemover,
    private val projector: PostsUsersProjection,
) {
    @PostMapping
    fun createUser(
        @Valid @RequestBody request: UserDtoValidator,
    ): ResponseEntity<IdentifierDtoOutput> {
        val dto =
            UserDto(
                name = request.name,
                email = request.email,
                password = request.password,
                id = UuidGenerator.random(),
            )
        val response = userCreator.execute(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getAllUsers(
        @RequestParam params: Map<String, String>,
    ): ResponseEntity<PaginationOutputDto<UserProjection>> {
        val page = params["page"]
        val limit = params["limit"]
        val like = params["like"]
        val pagination =
            PaginationInputDto(
                pageNumber = page?.toInt() ?: 1,
                limit = limit?.toInt() ?: 10,
                like = like,
            )
        return ResponseEntity.ok(projector.getUsersProjection(pagination = pagination))
    }

    @GetMapping("/{id}")
    fun getUserById(
        @Valid @PathVariable("id") id: String,
    ): ResponseEntity<UserProjection> {
        val response = projector.getUserProjection(UuidGenerator.fromString(id))
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @Valid @PathVariable("id") id: String,
    ): ResponseEntity<EmptyResponse> {
        userRemover.execute(UuidGenerator.fromString(id))
        return ResponseEntity.ok(EmptyResponse())
    }

    @PutMapping("/{id}")
    fun updateUserById(
        @RequestBody request: UpdateUserDtoValidator,
        @PathVariable("id") id: String,
    ): ResponseEntity<EmptyResponse> {
        val dto =
            UpdateUserDto(
                id = id,
                name = request.name,
                email = request.email,
            )
        userUpdater.execute(dto)
        return ResponseEntity.ok(EmptyResponse())
    }
}
