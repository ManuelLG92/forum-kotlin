package com.mleon.forumkotlin.users.infrastructure.persistence

import com.mleon.forumkotlin.shared.domain.UuidGenerator
import com.mleon.forumkotlin.shared.infrastructure.persistence.common.Pagination
import com.mleon.forumkotlin.shared.infrastructure.persistence.crud.CrudRepositoryImplementation
import com.mleon.forumkotlin.users.application.dto.input.UserDto
import com.mleon.forumkotlin.users.application.factory.UserFactory
import com.mleon.forumkotlin.users.domain.User
import com.mleon.forumkotlin.users.domain.UserCrudRepository
import com.mleon.forumkotlin.users.domain.UserEventPublisher
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRepositoryImplementation(publisher: UserEventPublisher) : UserCrudRepository,
    CrudRepositoryImplementation<User>(
        entity = User::class.toString(),
        publisher = publisher,
        pagination = Pagination(),
    ) {
    private final val id1 = "6f444d5b-af63-4d24-a6e7-9ca967510eba"
    private final val list =
        mutableListOf(
            "9feb8add-caad-41db-ac42-75499e7bf198",
            "e85b4f69-540a-431e-bfaa-dc0ffed12993",
            "56e30aaf-d5e2-458d-b346-cd8cad5dcc4c",
            "93b0a39f-2208-4f6d-8581-538c29dfdccf",
        )

    init {
        for (i in 1..10) {
            val random = if (list.size > 0) UUID.fromString(list.random()) else UuidGenerator.random()
            val userDto =
                UserDto(random ?: UuidGenerator.random(), "username $i", "email $i", UuidGenerator.random().toString())
            val user = UserFactory.create(userDto)
            this.save(user)
            list.remove(random.toString())
        }
        val userDto =
            UserDto(
                UuidGenerator.fromString(id1),
                "user name fixed",
                "user email fixed",
                "6f444d5b-af63-4d24-a6e7-9ca967510eba",
            )
        val user = UserFactory.create(userDto)
        this.save(user)
    }
}
