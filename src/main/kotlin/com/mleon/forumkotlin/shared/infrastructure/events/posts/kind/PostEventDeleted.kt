package com.mleon.forumkotlin.shared.infrastructure.events.posts.kind

import com.mleon.forumkotlin.shared.application.dto.input.PostDeletedDto

class PostEventDeleted(source: Any, message: String, val payload: PostDeletedDto) : ParentPostEvent(source, message)
