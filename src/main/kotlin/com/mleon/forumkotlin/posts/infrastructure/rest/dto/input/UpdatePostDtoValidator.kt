package com.mleon.forumkotlin.posts.infrastructure.rest.dto.input

import jakarta.validation.constraints.Size

data class UpdatePostDtoValidator(
    @field:Size(min = 2) val title: String,
    @field:Size(min = 3) val content: String,
)
