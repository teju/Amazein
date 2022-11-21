package com.amazein.ui.adapters

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.helper.Helper
import com.amazein.library.helper.ViewClickListener
import com.amazein.model.cert.HolderTag
import java.util.*

/**
 * Created by chanpyaeaung on 30/11/16.
 */
class HomeHealthRisksAdapter(private val context: Context, holderTags: List<HolderTag>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var holderTags: List<HolderTag> = ArrayList()
    var viewClickListener : ViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_health_risks, parent, false)
        return HomeHealthRisksViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val cardLimitViewHolder = holder as HomeHealthRisksViewHolder
            cardLimitViewHolder.itemView.tag = position

            val displaymetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
            //if you need three fix imageview in width
            val devicewidth = displaymetrics.widthPixels / 3
            cardLimitViewHolder.itemView.layoutParams.width = Helper.convertIntoDp(devicewidth,context).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return 5
    }

    private class HomeHealthRisksViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tv_description: TextView
        var tv_title: TextView
        var img_logo: ImageView

        init {
            tv_description = itemView.findViewById(R.id.tv_description)
            tv_title = itemView.findViewById(R.id.tv_title)
            img_logo = itemView.findViewById(R.id.img_logo)
        }
    }

    init {
        this.holderTags = holderTags
    }
}