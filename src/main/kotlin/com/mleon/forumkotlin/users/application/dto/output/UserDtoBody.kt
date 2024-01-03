package com.mleon.forumkotlin.users.application.dto.output

import com.mleon.forumkotlin.users.domain.User
import java.time.LocalDateTime
import java.util.UUID

data class UserDtoBody(
    val id: UUID,
    val name: String,
    val email: String,
    val posts: List<UUID>,
    var updated_at: LocalDateTime,
    val created_at: LocalDateTime,
) {
    companion object {
        fun fromUser(user: User): UserDtoBody {
            return UserDtoBody(
                id = user.id,
                name = user.name,
                email = user.email,
                updated_at = user.updatedAt,
                created_at = user.createdAt,
                posts = user.posts.values.toList(),
            )
        }
    }
}
