package com.mleon.forumkotlin.shared.domain

import com.mleon.forumkotlin.shared.domain.exceptions.BadRequestException
import java.util.UUID

class UuidGenerator {
    companion object {
        fun fromString(id: String): UUID {
            try {
                return UUID.fromString(id)
            } catch (e: IllegalArgumentException) {
                throw BadRequestException("Not valid UUID")
            }
        }

        fun random(): UUID {
            return UUID.randomUUID()
        }
    }
}
