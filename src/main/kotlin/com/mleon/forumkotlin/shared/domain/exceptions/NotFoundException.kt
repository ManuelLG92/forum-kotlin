package com.mleon.forumkotlin.shared.domain.exceptions

data class NotFoundException(override val message: String) : Exception()
