package com.mleon.forumkotlin.users.domain

import com.mleon.forumkotlin.shared.domain.AggregateRoot
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.domain.exceptions.BadRequestException
import java.time.LocalDateTime
import java.util.UUID

class User private constructor(
    override val id: UUID,
    var name: String,
    var email: String,
    var updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val password: String,
    var posts: MutableMap<UUID, UUID> = mutableMapOf(),
) : AggregateRoot() {
    private fun updateDate() {
        this.updatedAt = LocalDateTime.now()
    }

    fun update(
        name: String,
        email: String,
    ) {
        this.name = name
        this.email = email
        this.updateDate()
    }

    fun addPost(id: UUID) {
        this.getPostOrNull(id)
            .let { if (it !== null) throw BadRequestException("User ${this.id} already contain the post id $id") }
        this.posts[id] = id
        this.updateDate()
    }

    fun deletePost(id: UUID) {
        this.posts.remove(id)
            .let { if (it == null) throw BadRequestException("User ${this.id} doesn't contain post id $id") }
        this.updateDate()
    }

    private fun getPostOrNull(id: UUID): UUID? {
        return this.posts[id]
    }

    companion object {
        fun fromValues(
            name: String,
            email: String,
            password: String,
            id: UUID?,
        ): User {
            val now = LocalDateTime.now()
            return User(
                name = name,
                email = email,
                createdAt = now,
                updatedAt = now,
                password = password,
                id = id ?: UuidGenerator.random(),
            )
        }
    }
}
