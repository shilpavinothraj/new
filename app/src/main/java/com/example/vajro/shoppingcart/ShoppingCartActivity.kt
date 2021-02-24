package com.example.vajro.shoppingcart

import Products
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vajro.MainActivity
import com.example.vajro.R
import com.example.vajro.database.DatabaseHelper
import com.example.vajro.databinding.ActivityShoppingCartBinding
import com.example.vajro.productscreen.CartInterface
import com.example.vajro.productscreen.NetworkService
import product_response_Base
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShoppingCartActivity : AppCompatActivity() {
    lateinit var binding: ActivityShoppingCartBinding
    private var shoppingadapter: ShoppingCartAdapter?=null
    private var db: DatabaseHelper? = null
    private var productbase:product_response_Base?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        db = DatabaseHelper(this)
       // writedb()
        val cartAPI = NetworkService.instance.create(CartInterface::class.java)
        val getProductsRequest: Call<product_response_Base?>? = cartAPI.products()
        //context.showProgressDialog()
        getProductsRequest?.enqueue(object : Callback<product_response_Base?> {
            override fun onFailure(call: Call<product_response_Base?>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<product_response_Base?>,
                response: Response<product_response_Base?>
            ) {
                productbase=response?.body()
                  if (db!!.getCount() > 0) {
                    val values = db!!.allAuth
                    shoppingadapter = ShoppingCartAdapter(applicationContext,productbase!!.products,values.productid)
                    val mGridLayoutManager = GridLayoutManager(applicationContext, 1)
                    mGridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    binding.rvCartlist.setLayoutManager(mGridLayoutManager)
                    binding.rvCartlist.setNestedScrollingEnabled(false)
                    binding.rvCartlist.setAdapter(shoppingadapter)
                      binding.tvToolbarTitle.text="My Cart ("+values.productid.size+")"
                      binding.empty.visibility=View.GONE


                  }else{
                      binding.empty.visibility=View.VISIBLE
                  }
            }

        })
        binding.homeIcon.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            this!!.startActivity(intent)
        })
        binding.toolbarBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }
}