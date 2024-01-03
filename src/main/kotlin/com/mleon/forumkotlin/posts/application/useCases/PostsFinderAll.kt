package com.mleon.forumkotlin.posts.application.useCases

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsUsersProjection
import org.springframework.stereotype.Component

@Component
class PostsFinderAll(private val projection: PostsUsersProjection) :
    UseCasesHandler<PaginationInputDto, PaginationOutputDto<PostProjection>> {
    override fun execute(data: PaginationInputDto): PaginationOutputDto<PostProjection> {
        return projection.getPostsProjection(pagination = data)
    }
}
