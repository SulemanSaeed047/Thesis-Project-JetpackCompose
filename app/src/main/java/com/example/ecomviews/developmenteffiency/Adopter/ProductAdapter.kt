package com.example.ecomviews.developmenteffiency.Adopter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.API.ModelClasses.ProductsList

class ProductAdapter(private var productList: List<ProductsList>,private val onProductClick: (Long) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_single_product_card, parent, false)
        return ProductViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product, onProductClick)
    }
    override fun getItemCount() = productList.size
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.product_title)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val description: TextView = itemView.findViewById(R.id.product_description)
        private val rating: TextView = itemView.findViewById(R.id.product_rating)
        private val image: ImageView = itemView.findViewById(R.id.product_image)
        fun bind(product: ProductsList, onProductClick: (Long) -> Unit) {
            title.text = product.title
            productPrice.text = "$ ${product.price}"
            description.text = product.description
            rating.text = product.rating.rate.toString()

            Glide.with(itemView.context)
                .load(product.image)
                .into(image)

            itemView.setOnClickListener {
                onProductClick(product.id)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateProductList(newProducts: List<ProductsList>) {
        productList = newProducts
        notifyDataSetChanged()
    }
}
