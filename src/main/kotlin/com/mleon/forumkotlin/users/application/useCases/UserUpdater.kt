package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.users.application.dto.input.UpdateUserDto
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Component

@Component
class UserUpdater(private val repository: UserCrudRepository) : UseCasesHandler<UpdateUserDto, Unit> {
    override fun execute(data: UpdateUserDto) {
        val user = repository.get(UuidGenerator.fromString(data.id))
        user.update(name = data.name, email = data.email)
        repository.update(user)
    }
}
