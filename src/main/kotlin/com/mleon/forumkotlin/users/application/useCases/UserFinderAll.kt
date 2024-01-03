package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.application.dto.input.PaginationInputDto
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.users.application.dto.output.UserDtoBody
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Component

@Component
class UserFinderAll(private val repository: UserCrudRepository) : UseCasesHandler<PaginationInputDto, List<UserDtoBody>> {
    override fun execute(data: PaginationInputDto): List<UserDtoBody> {
        return repository.getAll(pagination = data)
            .data
            .map { UserDtoBody.fromUser(it) }
            .toList()
    }
}
