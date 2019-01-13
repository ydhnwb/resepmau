package com.starla.resepmau.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.starla.resepmau.R
import com.starla.resepmau.model.Favorite
import kotlinx.android.synthetic.main.single_list.view.*

class FavoriteAdapter(val context : Context, val mList : MutableList<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = FavoriteAdapter.ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.single_list, null, false))

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bindData(mList.get(p1), context)

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindData(model : Favorite, context: Context){
            itemView.tv_list_title.text = model.name
            itemView.tv_list_content.text = model.desc
            itemView.setOnClickListener { Toast.makeText(context, "You clicked ${model.name}", Toast.LENGTH_SHORT).show() }
        }
    }
}