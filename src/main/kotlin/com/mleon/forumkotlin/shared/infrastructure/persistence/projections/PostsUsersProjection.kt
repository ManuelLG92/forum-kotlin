package com.mleon.forumkotlin.shared.infrastructure.persistence.projections

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PostsUsersProjection(private val userProjection: UsersProjection, private val postsProjection: PostsProjection) {
    fun getPostProjection(id: UUID): PostProjection {
        val postCache = this.postsProjection.getProjectionOrNull(id)
        if (postCache !== null) {
            return postCache
        }

        val post = this.postsProjection.getOrThrown(id = id)
        val user = this.userProjection.getOrThrown(id = post.created_by)
        val response =
            PostProjection(
                id = post.id,
                title = post.title,
                content = post.content,
                created_at = post.created_at,
                updated_at = post.updated_at,
                user = this.userProjection.mapOutput(user),
            )
        this.postsProjection.setProjection(response.id, response)
        return response
    }

    fun getPostsProjection(pagination: PaginationInputDto): PaginationOutputDto<PostProjection> {
        val values =
            this.postsProjection.values().run {
                if (pagination.like != null) {
                    filter {
                        it.title.lowercase().contains(pagination.like.lowercase()) ||
                            it.content.lowercase().contains(pagination.like.lowercase())
                    }
                } else {
                    this
                }
            }.map { getPostProjection(it.id) }

        val paginator = Pagination<PostProjection>()
        return paginator.paginate(
            values = values,
            pagination = pagination,
        )
    }

    fun getUserProjection(id: UUID): UserProjection {
        val userCache = this.userProjection.getProjectionOrNull(id)
        if (userCache !== null) {
            return userCache
        }
        val user = this.userProjection.getOrThrown(id = id)
        val posts = this.postsProjection.getPostCollectionByUserId(id = id)
        val response =
            UserProjection(
                id = user.id,
                name = user.name,
                updated_at = user.updated_at,
                created_at = user.created_at,
                email = user.email,
                posts = this.postsProjection.mapOutputCollection(posts),
            )
        this.userProjection.setProjection(response.id, response)
        return response
    }

    fun getUsersProjection(pagination: PaginationInputDto): PaginationOutputDto<UserProjection> {
        val values =
            this.userProjection.values().run {
                if (pagination.like != null) {
                    filter {
                        it.name.lowercase().contains(pagination.like.lowercase())
                    }
                } else {
                    this
                }
            }.map { getUserProjection(it.id) }
        val paginator = Pagination<UserProjection>()
        return paginator.paginate(
            values = values,
            pagination = pagination,
        )
    }

    fun invalidateProjectionByUserId(id: UUID) {
        this.userProjection.remove(id)
        this.postsProjection.values().forEach {
            if (it.created_by.compareTo(id) == 0) {
                this.postsProjection.remove(id = it.id)
            }
        }
    }

    fun invalidateUserProjection(id: UUID) {
        this.userProjection.invalidateProjection(id)
    }

    fun invalidatePostProjection(id: UUID) {
        this.postsProjection.invalidateProjection(id)
    }
}
