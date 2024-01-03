package com.mleon.forumkotlin.posts.infrastructure.rest.dto.input

import jakarta.validation.constraints.Size

data class PostDtoValidator(
    @field:Size(min = 2) val title: String,
    @field:Size(min = 3) val content: String,
    @field:Size(min = 1) val created_by: String,
)
