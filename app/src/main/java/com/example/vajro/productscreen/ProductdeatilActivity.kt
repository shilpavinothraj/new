package com.example.vajro.productscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.vajro.MainActivity
import com.example.vajro.R
import com.example.vajro.database.DatabaseHelper
import com.example.vajro.databinding.ActivityMainBinding
import com.example.vajro.databinding.ActivityProductdeatilBinding
import com.example.vajro.shoppingcart.ShoppingCartActivity

class ProductdeatilActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductdeatilBinding
    var count:Int=1
    private var db: DatabaseHelper? = null
    var isadded:Boolean=false

    companion object {
        fun newInstance() = ProductdeatilActivity()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productdeatil)
        db = DatabaseHelper(this)
        binding.productName.text=SpannableStringBuilder( intent.getStringExtra("title"))
        binding.productPrice.text=SpannableStringBuilder( intent.getStringExtra("price"))
        binding.tvDescription.text=SpannableStringBuilder( intent.getStringExtra("detail"))
        var url:String=SpannableStringBuilder( intent.getStringExtra("image")).toString()
        binding.tvToolbarTitle.text=SpannableStringBuilder( intent.getStringExtra("title"))
        count=intent.getIntExtra("quantity",1)
        binding.tvCount.text=count.toString()
        var productid=SpannableStringBuilder( intent.getStringExtra("id"))
        isadded= intent.getBooleanExtra("isitemadded",false)
        if (isadded){
            binding.addcart.visibility=View.VISIBLE
        }else{
            binding.addcart.visibility=View.GONE
        }
        if (db!!.getCount() > 0) {
            val values = db!!.allAuth
            binding.cartCount.text=values.productid.size.toString()

        }else{
            binding.cartCount.text="0"
        }

        binding.toolbarBack.setOnClickListener(View.OnClickListener {
            onBackPressed()

        })
        binding.cartlayout.setOnClickListener(View.OnClickListener {
                var intent = Intent(this, ShoppingCartActivity::class.java)
                this!!.startActivity(intent)

        })
        binding.addcart.setOnClickListener(View.OnClickListener {
            writedb(binding.productName.text.toString(),binding.productPrice.text.toString(),url)
            binding.addcart.visibility=View.GONE
            if (db!!.getCount() > 0) {
                val values = db!!.allAuth
                binding.cartCount.text=values.productid.size.toString()

            }else{
                binding.cartCount.text="0"
            }
            Toast.makeText(this, "Added to your cart", Toast.LENGTH_SHORT).show()
        })
        binding.plusBtn.setOnClickListener(View.OnClickListener {
            count++;
            binding.tvCount.text=count.toString()

        })
        binding.minusBtn.setOnClickListener(View.OnClickListener {
            if (count>1){
                count--;
                binding.tvCount.text=count.toString()
            }
        })

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .dontAnimate().into(binding.productimage)
    }
    fun writedb(productid: String,price:String,image:String){
        try {
            db = DatabaseHelper(this)
            db!!.insertAuth(productid,count.toString(),price,image)

        } catch (e: Exception) {
        }
    }

}