package com.mleon.forumkotlin.shared.infrastructure.events.posts.listener

import com.mleon.forumkotlin.shared.application.dto.input.SharedPostDtoInput
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.ParentPostEvent
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventUpdated
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsUsersProjection
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PostCrudProjectionEventListener(
    private val postProjection: PostsProjection,
    private val postsUsersProjection: PostsUsersProjection,
) :
    ApplicationListener<ParentPostEvent> {
    @Async
    override fun onApplicationEvent(event: ParentPostEvent) {
        println("Event ${PostProjection::class.java.simpleName} listener on ${this::class.java.simpleName}.")
        println("Message ${event.message}")

        when (event) {
            is PostEventCreated -> {
                println("Handling PostEventCreated")
                this.upsertProjections(event.payload)
                println("Projection created")
            }

            is PostEventUpdated -> {
                println("Handling PostEventUpdated")
                this.upsertProjections(event.payload)
                println("Projection updated")
            }

            is PostEventDeleted -> {
                val id = event.payload.id
                println("Handling PostEventDeleted - id: $id")
                postProjection.remove(id = id)
                println("Projection deleted")
            }
        }
    }

    private fun upsertProjections(payload: SharedPostDtoInput) {
        postProjection.upsert(payload)
        postsUsersProjection.invalidatePostProjection(payload.id)
    }
}
