package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.application.dto.output.IdentifierDtoOutput
import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.users.application.dto.input.UserDto
import com.mleon.forumkotlin.users.application.factory.UserFactory
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Component

@Component
class UserCreator(private val repository: UserCrudRepository) : UseCasesHandler<UserDto, IdentifierDtoOutput> {
    override fun execute(data: UserDto): IdentifierDtoOutput {
        val user = UserFactory.create(data = data)
        repository.save(user)
        return IdentifierDtoOutput(id = user.id.toString())
    }
}
