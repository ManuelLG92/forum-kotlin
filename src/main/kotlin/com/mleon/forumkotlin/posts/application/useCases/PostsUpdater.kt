package com.mleon.forumkotlin.posts.application.useCases

import com.mleon.forumkotlin.posts.application.dto.input.UpdatePostDto
import com.mleon.forumkotlin.posts.domain.PostsCrudRepository
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import org.springframework.stereotype.Component

@Component
class PostsUpdater(private val repository: PostsCrudRepository) : UseCasesHandler<UpdatePostDto, Unit> {
    override fun execute(data: UpdatePostDto) {
        val post = repository.get(UuidGenerator.fromString(data.id))
        post.update(data.title, data.content)
        repository.update(post)
    }
}
