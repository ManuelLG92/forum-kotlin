package com.mleon.forumkotlin.posts.infrastructure.persistence

import com.mleon.forumkotlin.posts.application.dto.input.PostDto
import com.mleon.forumkotlin.posts.application.factory.PostFactory
import com.mleon.forumkotlin.posts.domain.PostEventPublisher
import com.mleon.forumkotlin.posts.domain.Posts
import com.mleon.forumkotlin.posts.domain.PostsCrudRepository
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import com.mleon.forumkotlin.shared.infrastructure.persistence.crud.CrudRepositoryImplementation
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PostRepositoryImplementation(eventPublisher: PostEventPublisher) : PostsCrudRepository,
    CrudRepositoryImplementation<Posts>(
        entity = Posts::class.toString(),
        publisher = eventPublisher,
        pagination = Pagination(),
    ) {
    private final val list =
        listOf(
            "9feb8add-caad-41db-ac42-75499e7bf198",
            "e85b4f69-540a-431e-bfaa-dc0ffed12993",
            "56e30aaf-d5e2-458d-b346-cd8cad5dcc4c",
            "93b0a39f-2208-4f6d-8581-538c29dfdccf",
        )

    init {

        for (i in 1..10) {
            val postDto = PostDto("post title $i", "post content $i", list.random())
            val post = PostFactory.create(postDto)
            this.save(post)
        }
    }

    override fun delete(id: UUID) {
        val entity = this.get(id = id)
        this.entities.remove(id)
        this.publisher.onDelete(id = id.toString(), mapOf("userId" to entity.createdBy.toString()))
    }
}
