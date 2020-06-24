package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.*
import java.util.*

class CartViewModel (application: Application) : AndroidViewModel(application) {

    val cartPrice = MutableLiveData<Double>(CartViewModel.order.price)

    val orderedProducts = MutableLiveData<MutableList<OrderedProduct>>(CartViewModel.orderedProducts)

    companion object {

        private var order = Order()

        private val orderedProducts = mutableListOf<OrderedProduct>()

        fun clear() {
            orderedProducts.clear()
            order = Order()
        }

        fun getFullOrder() : OrderWithOrderedProducts {
            order.time = Date().time
            return OrderWithOrderedProducts(order, orderedProducts)
        }

        fun addProduct(product: ProductVariants, quantity: Int) {
            if(compare(product)) {
                updateQuantity(product.product, quantity)
                return
            }

            val order = OrderedProduct(
                orderId = order.id,
                product = product.product,
                quantity = quantity)

            product.colors.forEach { if(it.checked) order.color = it.name}
            product.sizes.forEach { if(it.checked) order.size = it.size}

            orderedProducts.add(order)
            updatePrice()
        }

        fun updateQuantity(product: Product, quantity: Int) {
            orderedProducts.forEach {
                if(it.product.id == product.id) {
                    if (quantity > 0)
                        it.quantity = quantity
                    else
                        orderedProducts.remove(it)

                    updatePrice()
                    return
                }
            }
        }

        private fun compare(product: ProductVariants) : Boolean {
            var validateColor = false
            var validateSize = false

            orderedProducts.forEach { order ->
                if (order.product.id == product.product.id) {
                    product.colors.forEach {
                        if (it.checked)
                            validateColor = (order.color == it.name)
                    }
                    product.sizes.forEach {
                        if(it.checked)
                            validateSize = (order.color == it.size)
                    }

                    return validateColor && validateSize
                }
            }

            return false
        }

        private fun updatePrice() {
            order.price = orderedProducts.sumByDouble { it.quantity * it.product.price }
        }

    }


}