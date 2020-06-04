package br.com.arquitetoandroid.appcommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.ProductCategoryFragment
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.ProductCategory

class ProductCategoryAdapter(val context: Context) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    var list: List<ProductCategory> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_category, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: ProductCategory = list[position]
        holder.title.text = category.title
        holder.cardView.setOnClickListener {
            (context as ProductCategoryFragment.Callback).itemSelected(category)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.iv_category_icon)
        val title: TextView = itemView.findViewById(R.id.tv_category_title)
        val cardView: CardView = itemView.findViewById(R.id.cv_product_category)
    }

}