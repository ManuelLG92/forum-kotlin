package com.mleon.forumkotlin.shared.domain.exceptions

data class BadRequestException(override val message: String) : Exception()
