package com.mleon.forumkotlin.shared.infrastructure.persistence.crud

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.domain.AggregateRoot
import com.mleon.forumkotlin.shared.domain.DomainEventPublisher
import com.mleon.forumkotlin.shared.domain.exceptions.BadRequestException
import com.mleon.forumkotlin.shared.domain.exceptions.NotFoundException
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import java.util.UUID

abstract class CrudRepositoryImplementation<T : AggregateRoot>(
    private val entity: String,
    protected val publisher: DomainEventPublisher<T>,
    private val pagination: Pagination<T>,
) : CrudRepository<T> {
    var entities = mutableMapOf<UUID, T>()

    private val entityName = this.entity.split('.').last()

    override fun save(aggregateRoot: T) {
        when (exists(aggregateRoot.id)) {
            false -> entities[aggregateRoot.id] = aggregateRoot
            else -> throw BadRequestException("A record with id ${aggregateRoot.id} from $entityName already exists")
        }
        publisher.onCreate(data = aggregateRoot)
    }

    private fun exists(id: UUID): Boolean {
        entities[id].let {
            return it is T
        }
    }

    override fun getAll(pagination: PaginationInputDto): PaginationOutputDto<T> {
        return this.pagination.paginate(this.entities.values.toList(), pagination)
    }

    override fun get(id: UUID): T {
        entities[id].let {
            if (it is T) return it
            throw NotFoundException("$entityName id $id not found")
        }
    }

    override fun update(aggregateRoot: T) {
        when (exists(aggregateRoot.id)) {
            true -> entities[aggregateRoot.id] = aggregateRoot
            else -> throw throw NotFoundException("$aggregateRoot with ${aggregateRoot.id} not found")
        }
        publisher.onUpdate(data = aggregateRoot)
    }

    override fun delete(id: UUID) {
        entities.remove(id).let {
            if (it === null) throw NotFoundException("$entityName with $id not found")
        }
        publisher.onDelete(id = id.toString())
    }

    fun reset() {
        entities = mutableMapOf()
    }
}
