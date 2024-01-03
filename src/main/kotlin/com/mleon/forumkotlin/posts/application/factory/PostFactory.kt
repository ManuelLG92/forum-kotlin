package com.mleon.forumkotlin.posts.application.factory

import com.mleon.forumkotlin.posts.application.dto.input.PostDto
import com.mleon.forumkotlin.posts.domain.Posts
import com.mleon.forumkotlin.shared.domain.UuidGenerator

class PostFactory {
    companion object {
        fun create(data: PostDto): Posts {
            return Posts.fromValues(
                title = data.title,
                content = data.content,
                createdBy = UuidGenerator.fromString(data.createdBy),
            )
        }
    }
}
