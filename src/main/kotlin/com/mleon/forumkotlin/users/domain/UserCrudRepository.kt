package com.mleon.forumkotlin.users.domain

import com.mleon.forumkotlin.shared.infrastructure.persistence.crud.CrudRepository

interface UserCrudRepository : CrudRepository<User>
