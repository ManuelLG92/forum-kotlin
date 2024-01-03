package com.mleon.forumkotlin.users.infrastructure.events.publisher

import com.mleon.forumkotlin.shared.application.dto.input.SharedUserDtoInput
import com.mleon.forumkotlin.shared.domain.DomainEventPublisher
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventUpdated
import com.mleon.forumkotlin.users.domain.User
import com.mleon.forumkotlin.users.domain.UserEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class UsersEventPublisherImpl(private val applicationEventPublisher: ApplicationEventPublisher) :
    DomainEventPublisher<User>, UserEventPublisher {
    override fun onCreate(data: User) {
        val id = data.id
        val userEventCreated =
            UserEventCreated(
                this,
                "User $id created",
                this.sharedUserDtoInputFactory(data),
            )
        applicationEventPublisher.publishEvent(userEventCreated)
    }

    override fun onUpdate(data: User) {
        val id = data.id
        val userEventUpdated =
            UserEventUpdated(
                this,
                "User $id updated",
                this.sharedUserDtoInputFactory(data),
            )
        applicationEventPublisher.publishEvent(userEventUpdated)
    }

    override fun onDelete(
        id: String,
        metadata: Map<String, String>?,
    ) {
        val userEventDeleted =
            UserEventDeleted(
                this,
                "User $id Deleted",
                id,
            )
        applicationEventPublisher.publishEvent(userEventDeleted)
    }

    private fun sharedUserDtoInputFactory(user: User): SharedUserDtoInput {
        return SharedUserDtoInput(
            id = user.id,
            updated_at = user.updatedAt,
            created_at = user.createdAt,
            name = user.name,
            email = user.email,
        )
    }
}
