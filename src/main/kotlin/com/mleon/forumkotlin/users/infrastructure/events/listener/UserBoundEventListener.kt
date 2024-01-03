package com.mleon.forumkotlin.users.infrastructure.events.listener

import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.ParentUserEvent
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventCreated
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventDeleted
import com.mleon.forumkotlin.shared.infrastructure.events.user.kind.UserEventUpdated
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserBoundEventListener :
    ApplicationListener<ParentUserEvent> {
    @Async
    override fun onApplicationEvent(event: ParentUserEvent) {
        when (event) {
            is UserEventCreated -> {
                println("TODO: Handler user created events. Eg. send email verification")
            }

            is UserEventUpdated -> {
                println("TODO: Handler user updated events. Eg. send informative email")
            }

            is UserEventDeleted -> {
                println("TODO: Handler user deleted events. Eg. send email confirmation")
            }

            else -> throw RuntimeException("event not matched hierarchy")
        }
    }
}
