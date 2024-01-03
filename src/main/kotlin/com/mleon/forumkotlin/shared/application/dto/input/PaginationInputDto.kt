package com.mleon.forumkotlin.shared.application.dto.input

data class PaginationInputDto(val pageNumber: Int = 1, val limit: Int = 10, val like: String? = null)
