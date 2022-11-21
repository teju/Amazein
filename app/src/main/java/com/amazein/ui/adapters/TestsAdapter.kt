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


class TestsAdapter(val context: Context, arrayList: ArrayList<Any>) : RecyclerView.Adapter<TestsItemViewHolder>() {

    private var items: List<Base> = listOf()
    var itemClick : ViewClickListener?= null
    var isPAckages : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsItemViewHolder =
        TestsItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_tests, parent, false))

    override fun onBindViewHolder(holder: TestsItemViewHolder, position: Int) {
      //  holder.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick?.onViewClick(position)
       }
        if(isPAckages) {
            holder.tv_discount.visibility = View.VISIBLE
        } else{
            holder.tv_discount.visibility = View.GONE

        }
        setDetails(holder)

    }
    private fun setDetails(testsItemViewHolder: TestsItemViewHolder) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        testsItemViewHolder.rv_details.layoutManager = linearLayoutManager
        val testPacDetailsAdapter = TestPacDetailsAdapter(
            context, java.util.ArrayList()
        )
        testsItemViewHolder.rv_details.adapter = testPacDetailsAdapter
    }

    override fun getItemCount() = 10

}

class TestsItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var rv_details :RecyclerView
        var tv_discount :TextView
        init {
            rv_details = itemView.findViewById(R.id.rv_details)
            tv_discount = itemView.findViewById(R.id.tv_discount)

        }

}