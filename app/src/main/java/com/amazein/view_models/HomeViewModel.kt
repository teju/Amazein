package com.amazein.view_models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amazein.helper.Helper
import com.amazein.interfaces.ProgressListener
import com.amazein.model.home.Home
import com.amazein.repository.HomeRepo

class HomeViewModel( application: Application) : BaseViewModel(application) {
    var apl: Application

    private var homeRepo: HomeRepo? = null
    private val stringMutableLiveData = MutableLiveData<String>()
    var volumesResponseLiveData: LiveData<Home>? = null
        private set
    init {
        this.apl = application
    }

    fun init() {
        homeRepo = HomeRepo(object :ProgressListener {
            override fun onError(s: String?) {
                isLoading.postValue(false)
                errorMessage.postValue(s)
            }

            override fun preExecute() {
                isLoading.postValue(true)
            }

            override fun postExecute() {
                isLoading.postValue(false)
            }

        },apl)
        volumesResponseLiveData = homeRepo?.homeMutableLiveData!!
    }

    fun getHome() {
        if (!Helper.isNetworkAvailable(apl.applicationContext)) {
            isNetworkAvailable.postValue(false)
            isLoading.postValue(false)
            return
        }

        homeRepo?.getHome()
    }

    fun getItemsLiveData() :LiveData<String>  {
        return stringMutableLiveData;
    }

    fun refreshItems(tagID : String) {
        this.stringMutableLiveData.setValue(tagID)
    }
}