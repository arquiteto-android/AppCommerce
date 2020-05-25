package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "user_addresses")
data class UserAddress (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var userId: String,
    var addressLine1: String,
    var addressLine2: String,
    var number: String,
    var state: String,
    var city: String,
    var zipCode: String,
    var country: String) : Serializable