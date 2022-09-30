package com.example.sandisubmission2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Callback

class UserAdapter (private val listUser: List<ItemsUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var callback: OnItemCallback

    interface OnItemCallback {
        fun clicked(user: ItemsUser)


    }
    fun ItemClick(Clicked: OnItemCallback){
        this.callback = Clicked
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val bind: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.itemrow, viewGroup, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (avatar, id, name) = listUser[position]
        holder.nama.text = name
        holder.id.text = id.toString()
        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.avatar)

        holder.itemView.setOnClickListener{callback.clicked(listUser[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = view.findViewById(R.id.username)
        val id: TextView = view.findViewById(R.id.iduser)
        val avatar: ImageView = view.findViewById(R.id.imageView)

    }
}