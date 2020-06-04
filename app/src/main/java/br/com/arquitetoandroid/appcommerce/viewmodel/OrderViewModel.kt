package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.repository.OrdersRepository

class OrderViewModel (application: Application) : AndroidViewModel(application) {

    private val ordersRepository = OrdersRepository(getApplication())

    fun getOrdersByUser(userId: String) = ordersRepository.loadAllByUser(userId)

}