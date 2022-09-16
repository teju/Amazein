package com.amazein.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.amazein.R
import com.amazein.helper.Constants
import com.amazein.interfaces.ProgressListener
import com.amazein.model.home.Home
import com.amazein.web_services.HomeAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeRepo(private val listener: ProgressListener,val context : Context) {
    private val home: HomeAPI
    val homeMutableLiveData: MutableLiveData<Home>
    fun getHome() {
        home.home?.enqueue(object : Callback<Home?> {
                override fun onResponse(call: Call<Home?>, response: Response<Home?>) {
                    if (response.body() != null) {
                        listener.postExecute()
                        homeMutableLiveData.postValue(response.body())
                    } else{
                        listener.onError(context.getString(R.string.amazein__unknown_response))

                    }
                }

                override fun onFailure(call: Call<Home?>, t: Throwable) {
                    listener.onError(t.message)
                    homeMutableLiveData.postValue(null)
                }
            })
    }


    init {
        listener.preExecute()
        homeMutableLiveData = MutableLiveData()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        home = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeAPI::class.java)
    }
}