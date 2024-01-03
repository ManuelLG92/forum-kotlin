package com.mleon.forumkotlin.posts.application.useCases

import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsUsersProjection
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PostsFinder(private val projection: PostsUsersProjection) : UseCasesHandler<UUID, PostProjection> {
    override fun execute(data: UUID): PostProjection {
        return projection.getPostProjection(id = data)
    }
}
