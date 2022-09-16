package com.amazein.view_models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amazein.helper.Helper
import com.amazein.interfaces.ProgressListener
import com.amazein.model.cert.Certificate
import com.amazein.repository.CertificateRepo

class CertificateViewModel(application: Application) : BaseViewModel(application) {
    private var certificateRepo: CertificateRepo? = null
    var volumesResponseLiveData: LiveData<Certificate>? = null
        private set
    var apl: Application
    private val stringMutableLiveData = MutableLiveData<String>()

    init {
        this.apl = application
    }
    fun init() {
        certificateRepo = CertificateRepo(object :ProgressListener{
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
        volumesResponseLiveData = certificateRepo?.certMutableLiveData!!
    }

    fun verifyCert(certID: String) {
        if (!Helper.isNetworkAvailable(apl.applicationContext)) {
            isNetworkAvailable.postValue(false)
            isLoading.postValue(false)
            return
        }
        certificateRepo?.verifyCert(certID)
    }
}