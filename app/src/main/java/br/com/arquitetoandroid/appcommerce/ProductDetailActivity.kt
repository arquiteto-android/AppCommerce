package br.com.arquitetoandroid.appcommerce

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import br.com.arquitetoandroid.appcommerce.model.Product
import br.com.arquitetoandroid.appcommerce.model.ProductColor
import br.com.arquitetoandroid.appcommerce.model.ProductSize
import br.com.arquitetoandroid.appcommerce.model.ProductVariants
import br.com.arquitetoandroid.appcommerce.repository.ProductsRepository
import br.com.arquitetoandroid.appcommerce.viewmodel.CartViewModel
import br.com.arquitetoandroid.appcommerce.viewmodel.ProductViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ProductDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var textTitle: TextView
    lateinit var productPrice: TextView
    lateinit var productDesc: TextView
    lateinit var chipGroupColor: ChipGroup
    lateinit var chipGroupSize: ChipGroup
    lateinit var btnBuy: Button

    lateinit var product: Product
    lateinit var productVariants: ProductVariants

    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_const)

        product = intent.getSerializableExtra("PRODUCT") as Product

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        productViewModel.getProductWithVariants(product.id).observe(this, Observer {
            productVariants = it
            product = productVariants.product

            textTitle = findViewById(R.id.toolbar_title)
            textTitle.text = product.title

            productPrice = findViewById(R.id.tv_product_price)
            productPrice.text = "R$ ${product.price}"

            productDesc = findViewById(R.id.tv_product_desc)
            productDesc.text = product.description

            chipGroupColor = findViewById(R.id.chip_group_color)
            fillChipColor()

            chipGroupSize = findViewById(R.id.chip_group_size)
            fillChipSize()

            btnBuy = findViewById(R.id.btn_product_buy)
            btnBuy.setOnClickListener { addToCart() }
        })
    }

    private fun addToCart() {
        if(chipGroupColor.checkedChipId == View.NO_ID || chipGroupSize.checkedChipId == View.NO_ID) {
            Toast.makeText(
                this,
                getString(R.string.product_detail_cart_msg),
                Toast.LENGTH_SHORT
                ).show()
            return
        }

        findViewById<Chip>(chipGroupColor.checkedChipId).let {
            productVariants.colors[it.tag as Int].checked = true
        }

        findViewById<Chip>(chipGroupSize.checkedChipId).let {
            productVariants.sizes[it.tag as Int].checked = true
        }

        CartViewModel.addProduct(productVariants, 1)
        startActivity(Intent(this, CartActivity::class.java))
        finish()

    }

    fun fillChipColor() {
        val colors = productVariants.colors

        for (color in colors) {
            val chip = Chip(ContextThemeWrapper(chipGroupColor.context, R.style.Widget_MaterialComponents_Chip_Choice))
            chip.tag = colors.indexOf(color)
            chip.text = color.name
            chip.isCheckable = true
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(color.code))
            chip.chipStrokeWidth = 1.0F
            chip.chipStrokeColor = ColorStateList.valueOf(Color.GRAY)
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
            chipGroupColor.addView(chip)
        }
    }

    fun fillChipSize() {
        val sizes = productVariants.sizes

        for (size in sizes) {
            val chip = Chip(ContextThemeWrapper(chipGroupSize.context, R.style.Widget_MaterialComponents_Chip_Choice))
            chip.tag = sizes.indexOf(size)
            chip.text = size.size
            chip.isCheckable = true
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.WHITE)
            chip.chipStrokeWidth = 1.0F
            chip.chipStrokeColor = ColorStateList.valueOf(Color.GRAY)
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)))
            chipGroupSize.addView(chip)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
