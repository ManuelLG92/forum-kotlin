package com.mleon.forumkotlin.shared.application.dto.input

import com.mleon.forumkotlin.shared.application.dto.output.SharedAggregateRoot
import java.time.LocalDateTime
import java.util.UUID

open class SharedUserDtoInput(
    id: UUID,
    val name: String,
    val email: String,
    val updated_at: LocalDateTime,
    val created_at: LocalDateTime,
) : SharedAggregateRoot(id)
