package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.users.application.dto.output.UserDtoBody
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserFinder(private val repository: UserCrudRepository) : UseCasesHandler<UUID, UserDtoBody> {
    override fun execute(data: UUID): UserDtoBody {
        val user = repository.get(data)
        return UserDtoBody.fromUser(user = user)
    }
}
