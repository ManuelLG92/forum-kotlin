package com.mleon.forumkotlin.users.infrastructure.events.listener

import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.ParentPostEvent
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.posts.kind.PostEventUpdated
import com.mleon.forumkotlin.users.application.useCases.OnPostChangeAction
import com.mleon.forumkotlin.users.application.useCases.OnPostChangeActionDto
import com.mleon.forumkotlin.users.application.useCases.OnPostChangeActionEnum
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PostChangesEventListener(
    private val onPostChangeAction: OnPostChangeAction,
) :
    ApplicationListener<ParentPostEvent> {
    @Async
    override fun onApplicationEvent(event: ParentPostEvent) {
        println("Event ${event::class.java.simpleName} listener on ${this::class.java.simpleName}")
        println("Message ${event.message}")

        when (event) {
            is PostEventCreated -> {
                val postId = event.payload.id.toString()
                val userId = event.payload.created_by.toString()

                println("Handling PostEventCreated on user bound - post id: $postId ")

                this.onPostChangeAction.actionFactory(
                    dto =
                        OnPostChangeActionDto(
                            action = OnPostChangeActionEnum.ADD,
                            postId = postId,
                            userId = userId,
                        ),
                )
            }

            is PostEventUpdated -> {
                val postId = event.payload.id.toString()
                val userId = event.payload.created_by.toString()

                println("Handling PostEventUpdated on user bound - post id: $postId ")

                this.onPostChangeAction.actionFactory(
                    dto =
                        OnPostChangeActionDto(
                            action = OnPostChangeActionEnum.UPDATE,
                            postId = postId,
                            userId = userId,
                        ),
                )
                println("Post $postId updated from user $userId")
            }

            is PostEventDeleted -> {
                val postId = event.payload.id.toString()
                val userId = event.payload.userId.toString()
                println("Handling PostEventDeleted on user bound - post id: $postId ")
                this.onPostChangeAction.actionFactory(
                    dto =
                        OnPostChangeActionDto(
                            action = OnPostChangeActionEnum.REMOVE,
                            postId = postId,
                            userId = userId,
                        ),
                )
                println("Post $postId deleted from user $userId")
            }
        }
    }
}
