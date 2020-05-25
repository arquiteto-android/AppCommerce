package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_sizes")
data class ProductSize (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var productId: String,
    var size: String) : Serializable