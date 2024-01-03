package com.mleon.forumkotlin.shared.application.useCases

interface UseCasesHandler<I, O> {
    fun execute(data: I): O
}
