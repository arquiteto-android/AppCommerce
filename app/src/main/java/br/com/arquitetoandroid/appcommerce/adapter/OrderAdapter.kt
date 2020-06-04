package br.com.arquitetoandroid.appcommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(val context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var list: List<Order> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = list[position]
        holder.id.text = order.id
        holder.time.text = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(Date(order.time))
        holder.status.text = order.status.message
        holder.method.text = order.method.message
        holder.price.text = order.price.toString()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tv_id)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val method: TextView = itemView.findViewById(R.id.tv_method)
        val price: TextView = itemView.findViewById(R.id.tv_total)
    }
}