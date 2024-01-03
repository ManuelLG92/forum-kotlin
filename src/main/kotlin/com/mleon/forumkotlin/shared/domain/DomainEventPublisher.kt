package com.mleon.forumkotlin.shared.domain

interface DomainEventPublisher<T> {
    fun onCreate(data: T)

    fun onUpdate(data: T)

    fun onDelete(
        id: String,
        metadata: Map<String, String>? = mapOf(),
    )
}
