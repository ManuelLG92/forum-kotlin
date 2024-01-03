package com.mleon.forumkotlin.shared.infrastructure.persistence.projections

import com.mleon.forumkotlin.shared.application.dto.input.SharedUserDtoInput
import com.mleon.forumkotlin.shared.application.dto.output.SharedPostDtoBody
import com.mleon.forumkotlin.shared.application.dto.output.SharedUserDtoOutput
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

class UserProjection(
    id: UUID,
    name: String,
    email: String,
    updated_at: LocalDateTime,
    created_at: LocalDateTime,
    val posts: Collection<SharedPostDtoBody>,
) : SharedUserDtoInput(id = id, name = name, email = email, updated_at = updated_at, created_at = created_at)

@Service
class UsersProjection(override val pagination: Pagination<UserProjection>) :
    BaseProjection<SharedUserDtoInput, UserProjection>(pagination = pagination) {
    fun mapOutput(post: SharedUserDtoInput): SharedUserDtoOutput {
        return SharedUserDtoOutput(
            id = post.id,
            name = post.name,
        )
    }
}
