package com.amazein.ui.adapters

import android.R.attr.thumb
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.text.Html
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.helper.Helper
import com.amazein.interfaces.ViewClickListener
import com.amazein.model.Base

import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with



class ReviewsAdapter(val context: Context) : RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<Base> = listOf()
    var itemClick : ViewClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_reviews, parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
      //  holder.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick?.onViewClick(position)
       }
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        //if you need three fix imageview in width
        val devicewidth = displaymetrics.widthPixels / 3
        holder.itemView.layoutParams.width = Helper.convertIntoDp(devicewidth,context).toInt()
    }

    override fun getItemCount() = 5

}

class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Base) {

    }
}