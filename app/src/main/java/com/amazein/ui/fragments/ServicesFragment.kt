package com.amazein.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.helper.CenterZoomLinearLayoutManager
import com.amazein.ui.adapters.ReviewsAdapter
import com.amazein.ui.adapters.ServicesAdapter
import kotlinx.android.synthetic.main.fragment_services.*


class ServicesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_services, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setServicesAdapter()
    }

    fun setServicesAdapter(){
        val linearLayoutManageritem = LinearLayoutManager(activity!!,
            RecyclerView.VERTICAL,false)
        rv_services.layoutManager = linearLayoutManageritem
        val reviewsAdapter = ServicesAdapter(activity!!,ArrayList())
        rv_services.adapter = reviewsAdapter
    }

}