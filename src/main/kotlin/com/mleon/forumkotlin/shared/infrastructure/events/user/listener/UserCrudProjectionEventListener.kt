package com.mleon.forumkotlin.shared.infrastructure.events.user.listener

import com.mleon.forumkotlin.shared.application.dto.input.SharedUserDtoInput
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.ParentUserEvent
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventUpdated
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostsUsersProjection
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.UsersProjection
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserCrudProjectionEventListener(
    private val usersProjection: UsersProjection,
    private val postsUsersProjection: PostsUsersProjection,
) :
    ApplicationListener<ParentUserEvent> {
    @Async
    override fun onApplicationEvent(event: ParentUserEvent) {
        println("Event ${event::class.java.simpleName} listened on ${this::class.java.simpleName}")
        println("Message ${event.message}")

        when (event) {
            is UserEventCreated -> {
                println("Handling UserEventCreated")
                this.upsertProjections(event.payload)
                println("Projection created")
            }

            is UserEventUpdated -> {
                println("Handling UserEventUpdated")
                this.upsertProjections(event.payload)
                println("Projection updated")
            }

            is UserEventDeleted -> {
                println("Handling UserEventDeleted")
                val id = UuidGenerator.fromString(event.payload)
                this.postsUsersProjection.invalidateProjectionByUserId(id = id)
                this.usersProjection.remove(id = id)
                println("Projection deleted")
            }

            else -> throw RuntimeException("event not matched hierarchy")
        }
        println("Received user event on bounded context: ${event.message} ")
    }

    private fun upsertProjections(payload: SharedUserDtoInput) {
        usersProjection.upsert(payload)
        postsUsersProjection.invalidateUserProjection(payload.id)
    }
}
