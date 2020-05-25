package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithAddresses(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val addresses: List<UserAddress> = emptyList()
)