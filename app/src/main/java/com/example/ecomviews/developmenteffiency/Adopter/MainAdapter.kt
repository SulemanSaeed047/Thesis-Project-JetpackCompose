package com.example.ecomviews.developmenteffiency.Adopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomviews.R

class MainAdapter(
    private val data: List<Pair<String, Int>>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.ivIcon)
        val title: TextView = view.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = data[position]
        holder.icon.setImageResource(item.second)
        holder.title.text = item.first
        holder.itemView.setOnClickListener {
            val route = item.first
            onItemClicked(route)
        }
    }

    override fun getItemCount() = data.size
}
