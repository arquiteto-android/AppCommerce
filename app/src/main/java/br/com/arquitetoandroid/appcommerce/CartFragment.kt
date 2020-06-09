package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.CartAdapter
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel

class CartFragment  : Fragment() {

    lateinit var recyclerCart: RecyclerView

    private val cartViewModel by viewModels<CartViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerCart = view.findViewById(R.id.rv_cart)

        val adapterCart = CartAdapter(requireContext())

        cartViewModel.orderedProducts.observe(viewLifecycleOwner, Observer {
            adapterCart.list = it
            adapterCart.notifyDataSetChanged()
        })

        recyclerCart.adapter = adapterCart
        recyclerCart.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

    interface Callback {
        fun updateCart()
    }

}