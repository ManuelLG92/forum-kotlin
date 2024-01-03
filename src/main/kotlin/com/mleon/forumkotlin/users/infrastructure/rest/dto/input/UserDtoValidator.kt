package com.mleon.forumkotlin.users.infrastructure.rest.dto.input

import jakarta.validation.constraints.Size

data class UserDtoValidator(
    @field:Size(min = 2) val name: String,
    @field:Size(min = 3) val email: String,
    @field:Size(min = 1) val password: String,
)
