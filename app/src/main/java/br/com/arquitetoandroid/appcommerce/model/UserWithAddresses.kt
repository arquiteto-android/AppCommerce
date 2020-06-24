package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

data class UserWithAddresses(
    @Embedded var user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val addresses: MutableList<UserAddress> = mutableListOf()) {

    @Ignore constructor(): this(User(), mutableListOf())

}