package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithOrderedProducts (
    @Embedded val order: Order,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val producs: MutableList<OrderedProduct> = emptyList<OrderedProduct>().toMutableList())