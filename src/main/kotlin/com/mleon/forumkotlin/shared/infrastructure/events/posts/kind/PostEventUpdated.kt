package com.mleon.forumkotlin.shared.infrastructure.events.posts.kind

import com.mleon.forumkotlin.shared.application.dto.input.SharedPostDtoInput

class PostEventUpdated(source: Any, message: String, val payload: SharedPostDtoInput) :
    ParentPostEvent(source, message)
