package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_images")
data class ProductImage (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var productId: String,
    var path: String) : Serializable {

    @Ignore constructor(): this("", "", "")

}