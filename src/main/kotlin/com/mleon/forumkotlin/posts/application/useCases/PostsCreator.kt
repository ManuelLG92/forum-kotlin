package com.mleon.forumkotlin.posts.application.useCases

import com.mleon.forumkotlin.posts.application.dto.input.PostDto
import com.mleon.forumkotlin.posts.application.factory.PostFactory
import com.mleon.forumkotlin.posts.domain.PostsCrudRepository
import com.mleon.forumkotlin.shared.application.dto.output.IdentifierDtoOutput
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import org.springframework.stereotype.Component

@Component
class PostsCreator(private val repository: PostsCrudRepository) : UseCasesHandler<PostDto, IdentifierDtoOutput> {
    override fun execute(data: PostDto): IdentifierDtoOutput {
        val post = PostFactory.create(data)
        repository.save(post)
        return IdentifierDtoOutput(id = post.id.toString())
    }
}
