package com.mleon.forumkotlin.posts.domain

import com.mleon.forumkotlin.shared.domain.AggregateRoot
import java.time.LocalDateTime
import java.util.UUID

class Posts private constructor(
    var title: String,
    var content: String,
    var updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val createdBy: UUID,
) : AggregateRoot() {
    fun update(
        title: String,
        content: String,
    ) {
        this.title = title
        this.content = content
        this.updatedAt = LocalDateTime.now()
    }

    companion object {
        fun fromValues(
            title: String,
            content: String,
            createdBy: UUID,
        ): Posts {
            val now = LocalDateTime.now()
            return Posts(
                title = title,
                content = content,
                createdAt = now,
                updatedAt = now,
                createdBy = createdBy,
            )
        }
    }
}
