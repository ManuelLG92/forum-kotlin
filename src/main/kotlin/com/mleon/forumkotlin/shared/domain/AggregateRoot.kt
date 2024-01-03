package com.mleon.forumkotlin.shared.domain

import java.util.UUID

open class AggregateRoot {
    open val id: UUID = UuidGenerator.random()
}
