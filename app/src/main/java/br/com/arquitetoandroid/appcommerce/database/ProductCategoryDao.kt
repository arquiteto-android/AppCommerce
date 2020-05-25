package br.com.arquitetoandroid.appcommerce.database

import androidx.room.*
import br.com.arquitetoandroid.appcommerce.model.CategoryWithProducts
import br.com.arquitetoandroid.appcommerce.model.ProductCategory

@Dao
interface ProductCategoryDao {

    @Query("SELECT * FROM product_categories")
    fun loadAll() : List<ProductCategory>

    @Query("SELECT * FROM product_categories WHERE featured = 1")
    fun loadAllFeatured() : List<ProductCategory>

    @Transaction
    @Query("SELECT * FROM product_categories WHERE id = :categoryId")
    fun loadCategoryWithProductsById(categoryId: String) : List<CategoryWithProducts>

    @Insert
    fun insert(category: ProductCategory)

    @Update
    fun update(category: ProductCategory)
}