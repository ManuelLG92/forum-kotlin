package com.mleon.forumkotlin.shared.application.dto.output

import java.time.LocalDateTime
import java.util.UUID

open class SharedAggregateRoot(val id: UUID)

open class SharedPostDtoBody(
    id: UUID,
    val title: String,
    val content: String,
    val updated_at: LocalDateTime,
    val created_at: LocalDateTime,
) : SharedAggregateRoot(id)
