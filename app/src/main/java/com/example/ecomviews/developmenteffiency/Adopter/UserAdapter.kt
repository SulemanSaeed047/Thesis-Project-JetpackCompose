package com.example.ecomviews.developmenteffiency.Adopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.API.ModelClasses.SignUpUser

class UserAdapter(
    private val users: List<SignUpUser>,
    private val onUserClick: (Long) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.tvUsername)
        val email: TextView = view.findViewById(R.id.tvEmail)
        val profileImage: ImageView = view.findViewById(R.id.ivUserProfile)

        init {
            view.setOnClickListener {
                onUserClick(users[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.username.text = user.username
        holder.email.text = user.email
        holder.profileImage.setImageResource(R.drawable.user_profile)
    }

    override fun getItemCount() = users.size
}