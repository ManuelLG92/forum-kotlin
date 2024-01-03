package com.mleon.forumkotlin.posts.application.useCases

import com.mleon.forumkotlin.posts.domain.PostsCrudRepository
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PostsRemover(private val repository: PostsCrudRepository) : UseCasesHandler<UUID, Unit> {
    override fun execute(data: UUID) {
        return repository.delete(data)
    }
}
