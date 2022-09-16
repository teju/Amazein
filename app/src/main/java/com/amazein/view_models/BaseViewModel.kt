package com.amazein.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.amazein.helper.SingleLiveEvent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading = SingleLiveEvent<Boolean>()
    var isOauthExpired = SingleLiveEvent<Boolean>()
    var isNetworkAvailable = SingleLiveEvent<Boolean>()
    var isMaintenance = SingleLiveEvent<String>()
    var errorMessage = SingleLiveEvent<String>()

    inner class ErrorMessageModel {
        var isShouldDisplayDialog: Boolean = false
        var title: String? = null
        var message: String? = null
    }


    companion object {
        var isOauthExpiredSeamlessLogin: SingleLiveEvent<Boolean>? = null
    }

}