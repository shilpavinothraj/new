package com.example.vajro.productscreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import okhttp3.internal.Internal.instance
import product_response_Base
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel : ViewModel() {
    var productlistResponsedata = MutableLiveData<product_response_Base?>()

    fun productList(){
        val cartAPI = NetworkService.instance.create(CartInterface::class.java)
        val getProductsRequest: Call<product_response_Base?>? = cartAPI.products()
        //context.showProgressDialog()
       getProductsRequest?.enqueue(object :Callback<product_response_Base?>{
           override fun onFailure(call: Call<product_response_Base?>, t: Throwable) {
           }

           override fun onResponse(
               call: Call<product_response_Base?>,
               response: Response<product_response_Base?>
           ) {
               productlistResponsedata.value=response?.body()
           }

       })

    }
}