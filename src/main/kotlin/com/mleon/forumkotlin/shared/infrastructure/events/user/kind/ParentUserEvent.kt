package com.mleon.forumkotlin.shared.infrastructure.events.user.kind

import org.springframework.context.ApplicationEvent

open class ParentUserEvent(source: Any, val message: String) : ApplicationEvent(source)
