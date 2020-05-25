package br.com.arquitetoandroid.appcommerce.database

import androidx.room.*
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProducts

@Dao
interface OrderDao {

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :orderId")
    fun loadOrderAndProductsById(orderId: String) : List<OrderWithOrderedProducts>

    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun loadOrderByUser(userId: String) : List<Order>

    @Transaction
    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun loadOrderAndProductsByUser(userId: String) : List<OrderWithOrderedProducts>

    @Insert
    fun insert(order: Order)

    @Insert
    fun insertAll(vararg  orders: Order)

    @Update
    fun update(order: Order)
}