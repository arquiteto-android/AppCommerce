package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "products")
data class Product (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var title: String,
    var categoryId: String,
    var description: String,
    var price: Double,
    var thumbnail: String,
    var featured: Boolean = false) : Serializable {

    @Ignore constructor(): this("", "", "", "", 0.0, "", false)
}