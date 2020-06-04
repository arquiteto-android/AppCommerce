package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import br.com.arquitetoandroid.appcommerce.database.AppDatabase

class OrdersRepository (application: Application) {

    private val orderDao = AppDatabase.getDatabase(application).orderDao()

    fun loadAllByUser(userId: String) = orderDao.loadAllOrdersByUser(userId)

}