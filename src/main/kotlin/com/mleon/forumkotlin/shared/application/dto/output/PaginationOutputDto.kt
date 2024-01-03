package com.mleon.forumkotlin.shared.application.dto.output

data class PaginationOutputDto<T>(val data: List<T>, val pages: Int, val currentPage: Int)
