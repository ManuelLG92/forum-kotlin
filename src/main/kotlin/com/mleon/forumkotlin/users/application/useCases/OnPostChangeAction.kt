package com.mleon.forumkotlin.users.application.useCases

import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.users.domain.User
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import org.springframework.stereotype.Service
import java.util.UUID

enum class OnPostChangeActionEnum {
    ADD,
    REMOVE,
    UPDATE,
}

data class OnPostChangeActionDto(val action: OnPostChangeActionEnum, val userId: String, val postId: String)

@Service
class OnPostChangeAction(private val repository: UserCrudRepository) {
    private fun addPost(
        user: User,
        postId: UUID,
    ) {
        user.addPost(postId)
        repository.update(user)
    }

    private fun removePost(
        user: User,
        postId: UUID,
    ) {
        user.deletePost(postId)
        repository.update(user)
    }

    private fun updateOnPost(user: User) {
        repository.update(user)
    }

    fun actionFactory(dto: OnPostChangeActionDto) {
        val userId = UuidGenerator.fromString(dto.userId)
        val postUuid = UuidGenerator.fromString(dto.postId)
        val user = repository.get(userId)
        when (dto.action) {
            OnPostChangeActionEnum.ADD -> this.addPost(user, postUuid)
            OnPostChangeActionEnum.REMOVE -> this.removePost(user, postUuid)
            OnPostChangeActionEnum.UPDATE -> this.updateOnPost(user)
        }
    }
}
