package com.mleon.forumkotlin.users.application.factory

import com.mleon.forumkotlin.users.application.dto.input.UserDto
import com.mleon.forumkotlin.users.domain.User

class UserFactory {
    companion object {
        fun create(data: UserDto): User {
            return User.fromValues(
                name = data.name,
                email = data.email,
                password = data.password,
                id = data.id,
            )
        }
    }
}
