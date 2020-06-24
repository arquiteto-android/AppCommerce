package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import java.io.Serializable

data class OrderWithOrderedProducts (
    @Embedded var order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    var products: MutableList<OrderedProduct> = emptyList<OrderedProduct>().toMutableList()) : Serializable {

    @Ignore constructor(): this(Order(), mutableListOf())
}