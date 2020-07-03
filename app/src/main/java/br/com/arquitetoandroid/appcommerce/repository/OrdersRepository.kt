package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.database.AppDatabase
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.model.OrderWithOrderedProducts
import br.com.arquitetoandroid.appcommerce.model.OrderedProduct
import br.com.arquitetoandroid.appcommerce.model.UserWithAddresses
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mercadopago.android.px.model.Payment
import org.json.JSONArray
import org.json.JSONObject

class OrdersRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    private val queue = Volley.newRequestQueue(application)

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

    fun place(user: UserWithAddresses, fullOrder: OrderWithOrderedProducts) : LiveData<String> {

        val liveData = MutableLiveData<String>()

        val params = JSONObject().also {
            val items = JSONArray()
            fullOrder.products.forEach { product ->
                JSONObject().also { item ->
                    item.put("id", product.product.id)
                    item.put("category_id", product.product.categoryId)
                    item.put("title", "${product.product.title} (${product.size}, ${product.color})")
                    item.put("quantity", product.quantity)
                    item.put("unit_price", product.product.price)
                    items.put(item)
                }
            }

            val payer = JSONObject().also { payer ->
                payer.put("name", user.user.name)
                payer.put("surname", user.user.surname)
                payer.put("email", user.user.email)

                JSONObject().also { address ->
                    val userAddress = user.addresses.first()
                    address.put("zip_code", userAddress.zipCode)
                    address.put("street_name", userAddress.addressLine1)
                    address.put("street_number", userAddress.number)
                    payer.put("address", address)
                }
            }

            it.put("items", items)
            it.put("payer", payer)
        }

        Log.d(this.toString(), params.toString())

        val request = JsonObjectRequest(Request.Method.POST
            , BASE_URL + TOKEN
            , params
            , Response.Listener { response ->
                val id = response.getString("id")
                liveData.value = id
                Log.d(this.toString(), id)
            }
            , Response.ErrorListener { error ->
                Log.e(this.toString(), error.message ?: "Error")
            }
        )

        queue.add(request)

        return liveData
    }

    fun save(fullOrder: OrderWithOrderedProducts, payment: Payment) {

        fullOrder.order.method = Order.Method.CREDIT_CARD
        fullOrder.order.status = Order.Status.PAID

        val userRef = firestore.collection("users").document(fullOrder.order.userId)
        val orderREf = userRef.collection("orders").document(fullOrder.order.id)

        orderREf.set(fullOrder.order).addOnSuccessListener {
            fullOrder.products.forEach { product ->
                orderREf.collection("ordered_products")
                    .document(product.orderedProductId).set(product)
            }

            CartViewModel.clear()
        }
    }

    companion object {

        const val BASE_URL = "https://api.mercadopago.com/checkout/preferences"

        const val TOKEN = "?access_token=TEST-3413056325225889-082919-333bd7b8e0078784a0332904d6249daf-84704273"

    }
}