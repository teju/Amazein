package com.amazein.web_services

import com.amazein.model.home.Home
import retrofit2.Call
import retrofit2.http.GET

interface HomeAPI {
    @get:GET("/v1/home")
    val home: Call<Home?>?
}