package com.amazein.web_services

import com.amazein.model.cert.Certificate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CertificateAPI {
    @GET("/v1/certificate/verify/{id}")
    fun getVerifyCert(@Path("id") id: String): Call<Certificate>
}