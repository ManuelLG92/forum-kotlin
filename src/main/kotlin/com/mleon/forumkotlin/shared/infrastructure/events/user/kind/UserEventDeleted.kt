package com.mleon.forumkotlin.shared.infrastructure.events.user.kind

class UserEventDeleted(source: Any, message: String, val payload: String) : ParentUserEvent(source, message)
