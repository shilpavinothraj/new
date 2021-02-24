package com.example.vajro.productscreen


import product_response_Base
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface CartInterface {

        @GET("5def7b172f000063008e0aa2")
        fun products(): Call<product_response_Base?>?


}