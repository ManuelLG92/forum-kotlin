package com.mleon.forumkotlin.shared.infrastructure.persistence.projections

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.application.dto.output.SharedAggregateRoot
import com.mleon.forumkotlin.shared.domain.exceptions.BadRequestException
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import org.springframework.stereotype.Service
import java.util.UUID

@Service
abstract class BaseProjection<INPUT : SharedAggregateRoot, PROJECTION>(val pagination: Pagination<PROJECTION>) {
    private var context: String = this.javaClass.simpleName
    protected val entity: MutableMap<UUID, INPUT> = mutableMapOf()
    protected val entityProjection: MutableMap<UUID, PROJECTION> = mutableMapOf()

    fun upsert(data: INPUT) {
        entity[data.id] = data
        this.invalidateProjection(data.id)
    }

    fun getOrNull(id: UUID): INPUT? {
        return entity[id]
    }

    fun remove(id: UUID) {
        entity.remove(id)
        entityProjection.remove(id)
    }

    fun getAll(pagination: PaginationInputDto): PaginationOutputDto<PROJECTION> {
        return this.pagination.paginate(values = entityProjection.values.toList(), pagination = pagination)
    }

    fun getOrThrown(id: UUID): INPUT {
        return this.getOrNull(id) ?: throw BadRequestException("$context with $id doesn't exists")
    }

    fun values(): List<INPUT> {
        return entity.values.toList()
    }

    fun getProjectionOrNull(id: UUID): PROJECTION? {
        return entityProjection[id]
    }

    fun getProjectionOrThrow(id: UUID): PROJECTION {
        return this.getProjectionOrThrow(id = id) ?: throw BadRequestException("$context with $id doesn't exists")
    }

    fun invalidateProjection(id: UUID) {
        entityProjection.remove(id)
    }

    fun setProjection(
        id: UUID,
        data: PROJECTION,
    ) {
        entityProjection[id] = data
    }
}
