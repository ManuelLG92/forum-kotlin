package com.mleon.forumkotlin.users.infrastructure.rest.dto.input

import jakarta.validation.constraints.Size

data class UpdateUserDtoValidator(
    @field:Size(min = 2) val name: String,
    @field:Size(min = 3) val email: String,
)
