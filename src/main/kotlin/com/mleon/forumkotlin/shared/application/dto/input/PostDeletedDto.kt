package com.mleon.forumkotlin.shared.application.dto.input

import com.mleon.forumkotlin.shared.application.dto.output.SharedAggregateRoot
import java.util.UUID

open class PostDeletedDto(
    id: UUID,
    val userId: UUID,
) : SharedAggregateRoot(id)
