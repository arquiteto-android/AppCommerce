package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProducts
import br.com.arquitetoandroid.appcommerce.model.UserWithAddresses
import br.com.arquitetoandroid.appcommerce.repository.OrdersRepository
import com.mercadopago.android.px.model.Payment

class OrderViewModel (application: Application) : AndroidViewModel(application) {

    private val ordersRepository = OrdersRepository(getApplication())

    fun getOrdersByUser(userId: String) = ordersRepository.loadAllByUser(userId)

    fun place(user: UserWithAddresses, fullOrder: OrderWithOrderedProducts) = ordersRepository.place(user, fullOrder)

    fun save(fullOrder: OrderWithOrderedProducts, payment: Payment) = ordersRepository.save(fullOrder, payment)

}