package com.mleon.forumkotlin.posts.infrastructure.rest

import com.mleon.forumkotlin.posts.application.dto.input.PostDto
import com.mleon.forumkotlin.posts.application.dto.input.UpdatePostDto
import com.mleon.forumkotlin.posts.application.useCases.PostsCreator
import com.mleon.forumkotlin.posts.application.useCases.PostsFinder
import com.mleon.forumkotlin.posts.application.useCases.PostsFinderAll
import com.mleon.forumkotlin.posts.application.useCases.PostsRemover
import com.mleon.forumkotlin.posts.application.useCases.PostsUpdater
import com.mleon.forumkotlin.posts.infrastructure.rest.dto.input.PostDtoValidator
import com.mleon.forumkotlin.posts.infrastructure.rest.dto.input.UpdatePostDtoValidator
import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.dto.output.IdentifierDtoOutput
import com.mleon.forumkotlin.shared.application.dto.output.PaginationOutputDto
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.infrastructure.controller.EmptyResponse
import com.mleon.forumkotlin.shared.infrastructure.persistence.projections.PostProjection
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("posts")
@RestController
@Validated
class PostController(
    private val creator: PostsCreator,
    private val finder: PostsFinder,
    private val remover: PostsRemover,
    private val updater: PostsUpdater,
    private val finderAll: PostsFinderAll,
) {
    @PostMapping
    fun createPost(
        @Valid @RequestBody request: PostDtoValidator,
    ): ResponseEntity<IdentifierDtoOutput> {
        val dto = PostDto(request.title, request.content, request.created_by)
        val result = creator.execute(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @GetMapping
    fun getAllPosts(
        @RequestParam params: Map<String, String>,
    ): ResponseEntity<PaginationOutputDto<PostProjection>> {
        val page = params["page"]
        val limit = params["limit"]
        val like = params["like"]
        val pagination =
            PaginationInputDto(
                pageNumber = page?.toInt() ?: 1,
                limit = limit?.toInt() ?: 10,
                like = like,
            )
        return ResponseEntity.ok(finderAll.execute(data = pagination))
    }

    @GetMapping("{id}")
    fun getById(
        @Valid @PathVariable("id") id: String,
    ): ResponseEntity<PostProjection> {
        val post = finder.execute(UuidGenerator.fromString(id))
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("{id}")
    fun deletePostById(
        @Valid @PathVariable("id") id: String,
    ): ResponseEntity<EmptyResponse> {
        remover.execute(UuidGenerator.fromString(id))
        return ResponseEntity.ok(EmptyResponse())
    }

    @PutMapping("{id}")
    fun updatePostById(
        @RequestBody request: UpdatePostDtoValidator,
        @PathVariable("id") id: String,
    ): ResponseEntity<EmptyResponse> {
        val dto = UpdatePostDto(id = id, title = request.title, content = request.content)
        updater.execute(dto)
        return ResponseEntity.ok(EmptyResponse())
    }
}
