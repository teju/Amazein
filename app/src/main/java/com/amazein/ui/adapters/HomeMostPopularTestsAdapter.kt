package com.amazein.ui.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.helper.BaseUIHelper
import com.amazein.helper.Helper
import com.amazein.interfaces.ViewClickListener
import com.amazein.model.cert.HolderTag
import java.util.*

/**
 * Created by chanpyaeaung on 30/11/16.
 */
class HomeMostPopularTestsAdapter(private val context: Context, holderTags: List<HolderTag>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var holderTags: List<HolderTag> = ArrayList()
    var viewClickListener :ViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_popular_tests, parent, false)
        return HomeHealthPackageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val cardLimitViewHolder = holder as HomeHealthPackageViewHolder
            cardLimitViewHolder.itemView.tag = position
            setDetails(cardLimitViewHolder)
            val displaymetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
            //if you need three fix imageview in width
            val devicewidth = displaymetrics.widthPixels / 3
            if(position == 5) {
                cardLimitViewHolder.cv_root.visibility = View.GONE
                cardLimitViewHolder.view_all.visibility = View.VISIBLE

                cardLimitViewHolder.itemView.layoutParams.width = Helper.convertIntoDp(200,context).toInt()

            } else {
                cardLimitViewHolder.cv_root.visibility = View.VISIBLE
                cardLimitViewHolder.view_all.visibility = View.GONE
                cardLimitViewHolder.itemView.layoutParams.width = Helper.convertIntoDp(devicewidth,context).toInt()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setDetails(cardLimitViewHolder: HomeHealthPackageViewHolder) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        cardLimitViewHolder.rv_details.layoutManager = linearLayoutManager
        val homeHealthPackageAdapter = HomeHealthPacDetailsAdapter(
            context, ArrayList()
        )
        cardLimitViewHolder.rv_details.adapter = homeHealthPackageAdapter
    }

    override fun getItemCount(): Int {
        return 6
    }

    private class HomeHealthPackageViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tv_discount_price: TextView
        var rv_details: RecyclerView
        var cv_root: CardView
        var view_all: ImageView
        init {
            tv_discount_price = itemView.findViewById(R.id.tv_discount_price)
            rv_details = itemView.findViewById(R.id.rv_details)
            cv_root = itemView.findViewById(R.id.cv_root)
            view_all = itemView.findViewById(R.id.view_all)
        }
    }

    init {
        this.holderTags = holderTags
    }
}