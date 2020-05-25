package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.adapter.OrderAdapter
import br.com.arquitetoandroid.appcommerce.model.*
import java.util.*

class OrderFragment : Fragment() {

    lateinit var recyclerOrder: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_order, container, false)

        recyclerOrder = view.findViewById(R.id.rv_order)

        val adapterOrder = OrderAdapter(emptyList(), requireContext())

        recyclerOrder.adapter = adapterOrder
        recyclerOrder.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return view
    }

}