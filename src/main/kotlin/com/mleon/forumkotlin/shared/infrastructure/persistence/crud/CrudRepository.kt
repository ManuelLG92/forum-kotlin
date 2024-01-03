package com.mleon.forumkotlin.shared.infrastructure.persistence.crud

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import java.util.UUID

interface CrudRepository<T> {
    fun save(aggregateRoot: T)

    fun get(id: UUID): T

    fun getAll(pagination: PaginationInputDto): PaginationOutputDto<T>

    fun delete(id: UUID)

    fun update(aggregateRoot: T)
}
