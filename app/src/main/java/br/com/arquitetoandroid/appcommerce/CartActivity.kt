package br.com.arquitetoandroid.appcommerce

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.model.Order
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.OrderViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.UserViewModel
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError

class CartActivity : AppCompatActivity(), CartFragment.Callback {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var cartTotal: TextView
    lateinit var btnFinish: Button

    private val cartViewModel by viewModels<CartViewModel>()

    private val orderViewModel by viewModels<OrderViewModel>()

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        textTitle = findViewById(R.id.toolbar_title)
        textTitle.text = getString(R.string.cart_title)

        cartTotal = findViewById(R.id.tv_total)
        btnFinish = findViewById(R.id.btn_finish)

        btnFinish.setOnClickListener {
            userViewModel.isLogged().observe(this, Observer {
                if (it != null) {
                    val fullOrder = CartViewModel.getFullOrder()
                    fullOrder.order.userId = it.user.id
                    orderViewModel.place(it, fullOrder).observe(this, Observer {id ->
                        MercadoPagoCheckout.Builder("TEST-4b2a858c-a66b-444d-891a-46d94be39ef0", id).build()
                            .startPayment(this, 1)
                    })
                } else {
                    startActivity(Intent(this, UserLoginActivity::class.java))
                    finish()
                }
            })
        }

        cartViewModel.cartPrice.observe(this, Observer {
            cartTotal.text = "R$ ${it}"
        })

        val fragment = CartFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_cart, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun updateCart() {
        cartViewModel.cartPrice.value = CartViewModel.getFullOrder().order.price
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if(resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment = data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
                if(Payment.StatusCodes.STATUS_APPROVED == payment.paymentStatus) {
                    val fullOrder = CartViewModel.getFullOrder()
                    orderViewModel.save(fullOrder, payment)
                    startActivity(Intent(this, OrderActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this,
                    getString(R.string.cart_activity_payment_msg)
                    , Toast.LENGTH_LONG).show()
                }
            } else if(resultCode == Activity.RESULT_CANCELED && data != null) {
                val error = data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError
                Log.e(this.toString(), error.message)
            }
        }
    }
}