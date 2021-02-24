package com.example.vajro.shoppingcart

import Products
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vajro.R
import com.example.vajro.database.DatabaseHelper
import com.example.vajro.productscreen.ProductListAdapter
import com.example.vajro.productscreen.ProductdeatilActivity

class ShoppingCartAdapter  (val mContext: Context,val list: List<Products>,val selct:List<String>) :
RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    lateinit var view: View
    var count=1
    private var db: DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartAdapter.ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.shoppingcart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return selct.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: ShoppingCartAdapter.ViewHolder, position: Int) {
        holder.bindItems(selct.get(position), view, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(objectmenu: String, views: View, position: Int) {


            var productimage = view.findViewById<AppCompatImageView>(R.id.productimage)
            var product_heading = view.findViewById<AppCompatTextView>(R.id.product_name)
            var product_price = view.findViewById<AppCompatTextView>(R.id.product_price)
            var plus_btn = view.findViewById<AppCompatButton>(R.id.plus_btn)
            var minus_btn = view.findViewById<AppCompatButton>(R.id.minus_btn)
            var tvcount = view.findViewById<AppCompatTextView>(R.id.tv_count)
            var tot=view.findViewById<AppCompatTextView>(R.id.tv_product_tot_price)


            for (i in 0 until list!!.size){
                if (objectmenu.equals(list.get(i).product_id)){
                    Glide.with(mContext)
                        .load(list.get(i).image)
                        .placeholder(R.drawable.ic_launcher_background)
                        .dontAnimate().into(productimage)
                    product_heading.text=list.get(i).name
                    product_price.text=list.get(i).price
                    tot.text=list.get(i).price
                }
            }
            plus_btn.setOnClickListener(View.OnClickListener {
                count++;
                tvcount.text=count.toString()
                db = DatabaseHelper(mContext)
//                db?.deleteNote(position)

            })
            minus_btn.setOnClickListener(View.OnClickListener {
                if (count>1){
                    count--;
                    tvcount.text=count.toString()
                }
            })

        }
    }
}