package com.amazein.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazein.R
import com.amazein.helper.BaseUIHelper
import com.amazein.helper.CenterZoomLinearLayoutManager
import com.amazein.helper.Helper
import com.amazein.helper.autolooppager.AutoLoopPager
import com.amazein.model.Error
import com.amazein.model.home.Home
import com.amazein.ui.adapters.*
import com.amazein.ui.dialogs.BottomSheetFragment
import com.amazein.view_models.HomeViewModel
import com.google.gson.Gson
import com.iapps.gon.etc.callback.NotifyListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : BaseFragment(),View.OnClickListener {

    private  var banners: java.util.ArrayList<Int> = ArrayList()
    private var bottomSheetDialog: BottomSheetFragment? = null
    lateinit var homeViewModel: HomeViewModel
    var homeData: Home? = null
    private val autoLoopPager: AutoLoopPager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setHomeObserver()

        initView()
        sethealthPackageData()
        sethealthRisksData()
        setMostPopularTestsData()
        setReviewsAdapter()
    }

    override fun onResume() {
        super.onResume()
    }

    fun initView() {

        var autoLoopPager = v?.findViewById(R.id.autoPager) as AutoLoopPager
        formData()
        val adapter = HomeHeaderAdapter(activity,banners)
        autoLoopPager.setAdapter(adapter)
        autoLoopPager.setAutoPlay(true);
        //autoLoopPager.setAspectRatio(16f / 9f);

    }

    fun setReviewsAdapter(){
        val linearLayoutManageritem = CenterZoomLinearLayoutManager(activity!!,
            RecyclerView.HORIZONTAL,false)
        rv_reviews.layoutManager = linearLayoutManageritem
        val reviewsAdapter = ReviewsAdapter(activity!!)
        rv_reviews.adapter = reviewsAdapter
    }

    private fun sethealthPackageData() {
        val linearLayoutManager = LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL,false)
        rv_health_packages.layoutManager = linearLayoutManager
        val homeHealthPackageAdapter = HomeHealthPackageAdapter(activity!!,ArrayList())
        rv_health_packages.adapter = homeHealthPackageAdapter
        BaseUIHelper.setSnapper(rv_health_packages)
    }

    private fun setMostPopularTestsData() {
        val linearLayoutManager = LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL,false)
        rv_popular_test.layoutManager = linearLayoutManager
        val homeMostPopularTestsAdapter = HomeMostPopularTestsAdapter(activity!!,ArrayList())
        rv_popular_test.adapter = homeMostPopularTestsAdapter
        BaseUIHelper.setSnapper(rv_popular_test)
    }

    private fun sethealthRisksData() {
        val linearLayoutManager = LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL,false)
        rv_health_risks.layoutManager = linearLayoutManager
        val healthRisksAdapter = HomeHealthRisksAdapter(activity!!,ArrayList())
        rv_health_risks.adapter = healthRisksAdapter
        BaseUIHelper.setSnapper(rv_health_risks)
    }


    private fun formData() {
        banners = ArrayList<Int>()
        banners?.add(R.drawable.banner_1)
        banners?.add(R.drawable.banner_2)
    }
    override fun onClick(v: View?) {
        when(v?.id) {


        }
    }

    fun setHomeObserver(){
        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        homeViewModel.init()
        homeViewModel.apply {
            this@HomeFragment.let { thisFragReference ->
                isLoading.observe(thisFragReference, Observer { aBoolean ->
                    if (aBoolean!!) {
                        ld.showLoadingV2()
                    } else {
                        ld.hide()
                    }
                })
                errorMessage.observe(thisFragReference, Observer { s ->
                    var error = Error()
                    try {
                        val gson = Gson()
                        val mResp = gson.fromJson(s, Home::class.java)
                        error = mResp.error
                    } catch (e: Exception) {

                    }

                    Helper.showErrorDialog(activity!!, error, object : NotifyListener {
                        override fun onButtonClicked(which: Int) {
                        }

                    })
                })

                isNetworkAvailable.observe(thisFragReference, obsNoInternet)

                volumesResponseLiveData?.observe(this@HomeFragment, object : Observer<Home?> {

                    override fun onChanged(home: Home?) {
                        if (home != null) {
                            homeData = home

                        }
                    }
                })
            }
        }

    }
}