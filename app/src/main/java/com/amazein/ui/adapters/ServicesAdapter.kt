package com.amazein.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.library.helper.ViewClickListener
import com.amazein.model.Base


class ServicesAdapter(val context: Context, arrayList: ArrayList<Any>) : RecyclerView.Adapter<ServicesItemViewHolder>() {

    private var items: List<Base> = listOf()
    var itemClick : ViewClickListener?= null
    var isPAckages : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesItemViewHolder =
        ServicesItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_services, parent, false))

    override fun onBindViewHolder(holder: ServicesItemViewHolder, position: Int) {
      //  holder.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick?.onViewClick(position)
       }
       

    }

    override fun getItemCount() = 10

}

class ServicesItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        init {


        }

}