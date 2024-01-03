package com.mleon.forumkotlin.shared.infrastructure.persistence.projections

import com.mleon.forumkotlin.shared.application.dto.input.SharedPostDtoInput
import com.mleon.forumkotlin.shared.application.dto.output.SharedPostDtoBody
import com.mleon.forumkotlin.shared.application.dto.output.SharedUserDtoOutput
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

class PostProjection(
    val id: UUID,
    val title: String,
    val content: String,
    val updated_at: LocalDateTime,
    val created_at: LocalDateTime,
    val user: SharedUserDtoOutput,
)

@Service
class PostsProjection(override val pagination: Pagination<PostProjection>) :
    BaseProjection<SharedPostDtoInput, PostProjection>(pagination = pagination) {
    fun getPostCollectionByUserId(id: UUID): List<SharedPostDtoInput> {
        return entity.filter { it.value.created_by == id }.values.toList()
    }

    fun mapOutput(post: SharedPostDtoInput): SharedPostDtoBody {
        return SharedPostDtoBody(
            id = post.id,
            title = post.title,
            content = post.content,
            updated_at = post.updated_at,
            created_at = post.created_at,
        )
    }

    fun mapOutputCollection(posts: List<SharedPostDtoInput>): List<SharedPostDtoBody> {
        return posts.map { this.mapOutput(it) }
    }
}
