package br.com.arquitetoandroid.appcommerce.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.CartFragment
import br.com.arquitetoandroid.appcommerce.ProductDetailActivity
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.OrderedProduct
import br.com.arquitetoandroid.appcommerce.repository.ProductsRepository
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel

class CartAdapter (val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val productsRepository = ProductsRepository(context.applicationContext as Application)

    var list: MutableList<OrderedProduct> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderedProduct = list[position]
        holder.title.text = orderedProduct.product.title
        holder.color.text = orderedProduct.color
        holder.size.text = orderedProduct.size
        holder.quantity.text = orderedProduct.quantity.toString()

        holder.qtdUp.setOnClickListener {
            orderedProduct.quantity+=1
            CartViewModel.updateQuantity(orderedProduct.product, orderedProduct.quantity)
            holder.quantity.text = orderedProduct.quantity.toString()
            updatePrice(holder, orderedProduct)
            (context as CartFragment.Callback).updateCart()
        }
        holder.qtdDown.setOnClickListener {
            orderedProduct.quantity-=1
            CartViewModel.updateQuantity(orderedProduct.product, orderedProduct.quantity)

            if (orderedProduct.quantity > 0) {
                holder.quantity.text = orderedProduct.quantity.toString()
                updatePrice(holder, orderedProduct)
            }

            (context as CartFragment.Callback).updateCart()
            notifyDataSetChanged()
        }

        productsRepository.loadThumbnail(orderedProduct.product, holder.image)

        updatePrice(holder, orderedProduct)
    }

    private fun updatePrice(holder: ViewHolder, orderedProduct: OrderedProduct) {
        holder.price.text = "R$ ${orderedProduct.product.price * orderedProduct.quantity}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_product_image)
        val title: TextView = itemView.findViewById(R.id.tv_product_title)
        val color: TextView = itemView.findViewById(R.id.tv_color)
        val size: TextView = itemView.findViewById(R.id.tv_size)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val quantity: TextView = itemView.findViewById(R.id.tv_qtd)
        val qtdUp: ImageView = itemView.findViewById(R.id.iv_qtd_up)
        val qtdDown: ImageView = itemView.findViewById(R.id.iv_qtd_down)
        val cardView: CardView = itemView.findViewById(R.id.cv_product_item)
    }
}