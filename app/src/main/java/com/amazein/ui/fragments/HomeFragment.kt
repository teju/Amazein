package com.amazein.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amazein.R
import com.amazein.helper.Helper
import com.amazein.helper.autolooppager.AutoLoopPager
import com.amazein.model.Error
import com.amazein.model.home.Home
import com.amazein.ui.adapters.HomeHeaderAdapter
import com.amazein.ui.dialogs.BottomSheetFragment
import com.amazein.view_models.HomeViewModel
import com.google.gson.Gson
import com.iapps.gon.etc.callback.NotifyListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : BaseFragment(),View.OnClickListener {

    private  var banners: java.util.ArrayList<String> = ArrayList()
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

    private fun formData() {
        banners = ArrayList<String>()
        banners?.add("http://ww1.sinaimg.cn/large/7a8aed7bjw1f20ruz456sj20go0p0wi3.jpg")
        banners?.add("http://ww1.sinaimg.cn/large/610dc034gw1f20vetaa9pj20ka0dh75n.jpg")
        banners?.add("http://ww4.sinaimg.cn/large/610dc034gw1f20vefirbij20ka0dhq44.jpg")
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