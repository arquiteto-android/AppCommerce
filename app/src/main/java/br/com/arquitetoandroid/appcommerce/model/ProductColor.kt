package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_colors")
data class ProductColor (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var productId: String,
    var name: String,
    var code: String) : Serializable