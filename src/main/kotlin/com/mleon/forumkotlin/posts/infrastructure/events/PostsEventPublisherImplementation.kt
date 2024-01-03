package com.mleon.forumkotlin.posts.infrastructure.events

import com.mleon.forumkotlin.posts.domain.PostEventPublisher
import com.mleon.forumkotlin.posts.domain.Posts
import com.mleon.forumkotlin.shared.application.dto.input.PostDeletedDto
import com.mleon.forumkotlin.shared.application.dto.input.SharedPostDtoInput
import com.mleon.forumkotlin.shared.domain.DomainEventPublisher
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventUpdated
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PostsEventPublisherImplementation(private val applicationEventPublisher: ApplicationEventPublisher) :
    DomainEventPublisher<Posts>, PostEventPublisher {
    override fun onCreate(data: Posts) {
        val id = data.id
        val postEventCreated =
            PostEventCreated(
                this,
                "Post $id created",
                this.sharedPostDtoBodyFactory(data),
            )
        applicationEventPublisher.publishEvent(postEventCreated)
    }

    override fun onUpdate(data: Posts) {
        val id = data.id
        val postEventUpdated =
            PostEventUpdated(
                this,
                "Post $id updated",
                this.sharedPostDtoBodyFactory(data),
            )
        applicationEventPublisher.publishEvent(postEventUpdated)
    }

    override fun onDelete(
        id: String,
        metadata: Map<String, String>?,
    ) {
        val userId = metadata?.get("userId") ?: throw RuntimeException("User id is required")
        val postEventUpdated =
            PostEventDeleted(
                source = this,
                message = "Post $id deleted",
                payload = PostDeletedDto(id = UuidGenerator.fromString(id), userId = UuidGenerator.fromString(userId)),
            )
        applicationEventPublisher.publishEvent(postEventUpdated)
    }

    private fun sharedPostDtoBodyFactory(posts: Posts): SharedPostDtoInput {
        return SharedPostDtoInput(
            id = posts.id,
            created_by = posts.createdBy,
            updated_at = posts.updatedAt,
            created_at = posts.createdAt,
            title = posts.title,
            content = posts.content,
        )
    }
}
