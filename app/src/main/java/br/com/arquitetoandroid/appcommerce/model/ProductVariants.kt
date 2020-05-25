package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductVariants (
    @Embedded val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val colors: List<ProductColor>,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val sizes: List<ProductSize>,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val images: List<ProductImage>)