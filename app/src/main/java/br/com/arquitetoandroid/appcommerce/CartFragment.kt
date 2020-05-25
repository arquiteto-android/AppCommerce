package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.CartAdapter
import br.com.arquitetoandroid.appcommerce.model.*

class CartFragment  : Fragment() {

    lateinit var recyclerCart: RecyclerView
    lateinit var order: Order

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerCart = view.findViewById(R.id.rv_cart)

        val adapterCart = CartAdapter(emptyList(), requireContext())

        recyclerCart.adapter = adapterCart
        recyclerCart.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

}