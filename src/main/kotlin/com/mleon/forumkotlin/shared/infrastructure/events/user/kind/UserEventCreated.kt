package com.mleon.forumkotlin.shared.infrastructure.events.user.kind

import com.mleon.forumkotlin.shared.application.dto.input.SharedUserDtoInput

class UserEventCreated(source: Any, message: String, val payload: SharedUserDtoInput) : ParentUserEvent(source, message)
