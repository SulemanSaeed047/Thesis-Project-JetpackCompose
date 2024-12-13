package com.example.ecomviews.developmenteffiency.Adopter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val categories: List<String>,
    private val selectedFilter: String,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = TextView(parent.context)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, selectedFilter, onCategoryClick)
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: String, selectedFilter: String, onCategoryClick: (String) -> Unit) {
            val textView = itemView as TextView
            textView.text = category
            textView.setPadding(12, 12, 12, 12)

            if (category == selectedFilter) {
                textView.setBackgroundColor(Color.parseColor("#FF6200EE"))
            } else {
                textView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}
