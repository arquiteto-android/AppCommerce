package br.com.arquitetoandroid.appcommerce.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts (
    @Embedded val category: ProductCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<Product>)