package com.mleon.forumkotlin.users.application.dto.input

import java.util.UUID

data class UserDto(val id: UUID?, val name: String, val email: String, val password: String)
