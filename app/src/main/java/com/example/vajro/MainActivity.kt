package com.example.vajro

import Products
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vajro.database.DatabaseHelper
import com.example.vajro.databinding.ActivityMainBinding
import com.example.vajro.productscreen.ProductListAdapter
import com.example.vajro.productscreen.ProductListViewModel
import com.example.vajro.shoppingcart.ShoppingCartActivity
import com.example.vajro.shoppingcart.ShoppingCartAdapter


class MainActivity : AppCompatActivity(),ProductListAdapter.OnItemClick {
    lateinit var binding: ActivityMainBinding
    private var productListAdapter:ProductListAdapter?=null
    lateinit var viewModel: ProductListViewModel
    private var db: DatabaseHelper = DatabaseHelper(this)
    override fun onResume() {
        db = DatabaseHelper(this)
        if (db!!.getCount() > 0) {
            val values = db!!.allAuth
            binding.cartCount.text=values.productid.size.toString()

        }else{
            binding.cartCount.text="0"
        }
        db.allAuth
        productListAdapter?.notifyDataSetChanged()
        super.onResume()
    }

    var isloaded=false
    companion object {
        fun newInstance() = MainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        db = DatabaseHelper(this)
        if (isInternetAvailable(this)){
            binding.progressBar.visibility=View.VISIBLE
            viewModel.productList()
        }else{
            showAlert()
        }
        if (db!!.getCount() > 0) {
            val values = db!!.allAuth
            binding.cartCount.text=values.productid.size.toString()

        }else{
            binding.cartCount.text="0"
        }

        binding.cartlayout.setOnClickListener(View.OnClickListener {
                 var intent = Intent(this, ShoppingCartActivity::class.java)
                this!!.startActivity(intent)

        })

            viewModel.productlistResponsedata.observe(this@MainActivity, Observer {
            it?.let {
                if (it.products!=null){
                    binding.progressBar.visibility=View.GONE
                    productListAdapter = ProductListAdapter(this@MainActivity,it?.products,this)
                    val mGridLayoutManager = GridLayoutManager(this, 2)
                    mGridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    binding.rvProducts.setLayoutManager(mGridLayoutManager)
                    binding.rvProducts.setNestedScrollingEnabled(false)
                    binding.rvProducts.setAdapter(productListAdapter)
                    isloaded=true
                }
            }
        })
        }
    fun isInternetAvailable(activity: Context?): Boolean {
        if (activity != null) {
            val cm =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo? = null
            if (cm != null && cm.activeNetworkInfo != null) activeNetworkInfo =
                cm.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected
        }
        return false
    }
    fun showAlert(){
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle("Network Error")
        builder.setMessage("Please check your internet connection")
            .setPositiveButton("ok",
                DialogInterface.OnClickListener { dialog, which -> // continue with delete
                    dialog.cancel()
                })

            .show()
    }

    override fun onClick(value: String?) {
        binding.cartCount.text=value
    }

}