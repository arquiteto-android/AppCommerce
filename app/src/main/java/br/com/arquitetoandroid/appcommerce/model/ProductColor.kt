package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "product_colors")
data class ProductColor (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    var productId: String,
    var name: String,
    var code: String,
    @Ignore var checked: Boolean = false) : Serializable {

    constructor(id: String,
                productId: String,
                name: String,
                code: String) : this(id, productId, name, code, false)
}