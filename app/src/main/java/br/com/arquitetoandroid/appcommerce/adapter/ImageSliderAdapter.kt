package br.com.arquitetoandroid.appcommerce.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.arquitetoandroid.appcommerce.R
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.repository.ProductsRepository

class ImageSliderAdapter (val context: Context) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    private val productsRepository = ProductsRepository(context.applicationContext as Application)

    var product: ProductVariants = ProductVariants()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = product.images[position]
        productsRepository.loadImages(product.product, image.path, holder.imageView)
    }

    override fun getItemCount(): Int = product.images.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_photo)
    }
}