package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.RoomWarnings
import java.io.Serializable
import java.util.*

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "ordered_products", primaryKeys = ["orderedProductId", "orderId"])
data class OrderedProduct (
    var orderedProductId: String = UUID.randomUUID().toString(),
    var orderId: String,
    @Embedded val product: Product,
    var size: String = "",
    var color: String = "",
    var quantity: Int = 0) : Serializable {

    @Ignore constructor(): this(UUID.randomUUID().toString(), "", Product(), "", "", 0)

}