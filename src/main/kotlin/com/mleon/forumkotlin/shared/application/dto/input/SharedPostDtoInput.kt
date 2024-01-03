package com.mleon.forumkotlin.shared.application.dto.input

import com.mleon.forumkotlin.shared.application.dto.output.SharedAggregateRoot
import java.time.LocalDateTime
import java.util.UUID

open class SharedPostDtoInput(
    id: UUID,
    val title: String,
    val content: String,
    val updated_at: LocalDateTime,
    val created_at: LocalDateTime,
    val created_by: UUID,
) : SharedAggregateRoot(id)
