package com.mleon.forumkotlin.users.domain

import com.mleon.forumkotlin.shared.domain.DomainEventPublisher

interface UserEventPublisher : DomainEventPublisher<User>
