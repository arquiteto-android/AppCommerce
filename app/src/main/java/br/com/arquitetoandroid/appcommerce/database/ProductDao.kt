package br.com.arquitetoandroid.appcommerce.database

import androidx.room.*
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductVariants

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    fun loadAllByCategory(categoryId: String) : List<Product>

    @Query("SELECT * FROM products WHERE featured = 1")
    fun loadAllFeatured() : List<Product>

    @Transaction
    @Query("SELECT * FROM products WHERE id = :productId")
    fun loadProductWithVariants(productId: String) : ProductVariants

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)
}