package com.amazein.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.amazein.helper.Constants
import com.amazein.interfaces.ProgressListener
import com.amazein.model.cert.Certificate
import com.amazein.web_services.CertificateAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CertificateRepo(private val listener: ProgressListener, val context: Context) {
    private val certificateAPI: CertificateAPI
    val certMutableLiveData: MutableLiveData<Certificate>
    fun verifyCert(certID: String) {
        certificateAPI.getVerifyCert(certID)
            .enqueue(object : Callback<Certificate?> {
                override fun onResponse(
                    call: Call<Certificate?>,
                    response: Response<Certificate?>
                ) {
                    if (response.body() != null) {
                        listener.postExecute()
                        certMutableLiveData.postValue(response.body())
                    } else {
                        listener.onError(response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<Certificate?>, t: Throwable) {
                    listener.onError(t.message)
                    certMutableLiveData.postValue(null)
                }
            })
    }

    init {
        listener.postExecute()
        certMutableLiveData = MutableLiveData()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        certificateAPI = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CertificateAPI::class.java)
    }
}