package com.mleon.forumkotlin.shared.application.dto.output

import java.util.UUID

open class SharedUserDtoOutput(
    id: UUID,
    val name: String,
) : SharedAggregateRoot(id)
