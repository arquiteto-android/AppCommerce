package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProducts
import br.com.arquitetoandroid.appcommerce.model.OrderedProduct
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrdersRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    fun loadAllByUser(userId: String) : LiveData<List<Order>> {

        val liveData = MutableLiveData<List<Order>>()

        firestore.collection("users").document(userId)
            .collection("orders")
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, e ->

                if(e != null) return@addSnapshotListener

                liveData.value = snap?.toObjects(Order::class.java)
            }

        return liveData
    }

    fun place(fullOrder: OrderWithOrderedProducts) {

        val userRef = firestore.collection("users").document(fullOrder.order.userId)
        val orderRef = userRef.collection("orders").document(fullOrder.order.id)

        orderRef.set(fullOrder.order).addOnSuccessListener {

            fullOrder.products.forEach { product ->
                orderRef.collection("ordered_products")
                    .document(product.orderedProductId).set(product)
            }

            CartViewModel.clear()
        }
    }

}