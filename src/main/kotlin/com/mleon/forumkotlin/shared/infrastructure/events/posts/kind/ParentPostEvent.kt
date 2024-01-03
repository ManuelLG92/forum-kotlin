package com.mleon.forumkotlin.shared.infrastructure.events.posts.kind

import org.springframework.context.ApplicationEvent

open class ParentPostEvent(source: Any, val message: String) : ApplicationEvent(source)
