package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.application.useCases.UseCasesHandler
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRemover(private val repository: UserCrudRepository) : UseCasesHandler<UUID, Unit> {
    override fun execute(data: UUID) {
        return repository.delete(id = data)
    }
}
