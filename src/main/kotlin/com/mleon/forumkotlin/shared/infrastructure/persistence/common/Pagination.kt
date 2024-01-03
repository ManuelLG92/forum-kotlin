package com.mleon.forumkotlin.shared.infrastructure.persistence.common

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import org.springframework.stereotype.Component
import kotlin.math.ceil
import kotlin.math.min

@Component
class Pagination<OUT> {
    fun paginate(
        values: List<OUT>,
        pagination: PaginationInputDto,
    ): PaginationOutputDto<OUT> {
        val limit = pagination.limit
        val totalPages = ceil(values.size.toDouble() / limit).toInt()
        if (totalPages == 0) {
            return PaginationOutputDto(
                data = listOf(),
                pages = totalPages,
                currentPage = totalPages,
            )
        }
        val paginationPageNumber = pagination.pageNumber
        val pageNumber = if (paginationPageNumber > totalPages) totalPages else paginationPageNumber
        val startIndex = (pageNumber - 1) * limit
        val endIndex = min(startIndex + limit, values.size)
        val result = values.slice(startIndex until endIndex)

        return PaginationOutputDto(
            data = result,
            pages = totalPages,
            currentPage = pageNumber,
        )
    }
}
