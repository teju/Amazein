package com.amazein.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazein.R
import com.amazein.helper.BaseUIHelper
import com.amazein.ui.adapters.TestsAdapter
import kotlinx.android.synthetic.main.fragment_tab_tests.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PackagesTabFragment : BaseFragment(),View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tab_tests, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setHomeObserver()

        initView()
        setMostPopularTestsData()

    }

    override fun onResume() {
        super.onResume()
    }

    fun initView() {
        tv_test_count.setText("Showing all 111 results")


    }

    private fun setMostPopularTestsData() {
        val testsAdapter = TestsAdapter(activity!!,ArrayList())
        testsAdapter.isPAckages = true
        rv_tests.adapter = testsAdapter
    }

    override fun onClick(v: View?) {
        when(v?.id) {


        }
    }
}