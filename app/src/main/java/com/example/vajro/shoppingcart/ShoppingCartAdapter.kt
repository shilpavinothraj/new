package com.example.vajro.shoppingcart

import Products
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.vajro.database.SqliteHelper
import com.example.vajro.productscreen.ProductListAdapter
import com.example.vajro.productscreen.ProductdeatilActivity

class ShoppingCartAdapter  (val mContext: Context,val onItemClick: OnDeleteItemClick) :
RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    lateinit var view: View
    var count=1
    private var db: DatabaseHelper=DatabaseHelper(mContext)
    var values=db!!.allAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartAdapter.ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.shoppingcart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return db.count
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: ShoppingCartAdapter.ViewHolder, position: Int) {
        holder.bindItems(values.productid.get(position),values.quantity.get(position),values.image.get(position),values.price.get(position), view, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(name: String,quantity:String,image:String,price:String, views: View, position: Int) {


            var productimage = view.findViewById<AppCompatImageView>(R.id.productimage)
            var product_heading = view.findViewById<AppCompatTextView>(R.id.product_name)
            var product_price = view.findViewById<AppCompatTextView>(R.id.product_price)
            var plus_btn = view.findViewById<AppCompatButton>(R.id.plus_btn)
            var minus_btn = view.findViewById<AppCompatButton>(R.id.minus_btn)
            var tvcount = view.findViewById<AppCompatTextView>(R.id.tv_count)
            var tot=view.findViewById<AppCompatTextView>(R.id.tv_product_tot_price)
            var delete=view.findViewById<AppCompatButton>(R.id.btn_delete)


            Glide.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate().into(productimage)
            product_heading.text=name
            product_price.text=price
            tvcount.text=quantity
            count=quantity.toInt()
            var pp=price.replace(",","")
            tot.text="₹"+(pp.substring(1).toInt()*quantity.toInt()).toString()
            plus_btn.setOnClickListener(View.OnClickListener {
                count++;
                tvcount.text=count.toString()
                tot.text="₹"+(pp.substring(1).toInt()*count).toString()
                db!!.updateNote(name,count.toString(),price,image.toString())
//                db?.deleteNote(position)

            })
            minus_btn.setOnClickListener(View.OnClickListener {
                if (count>1){
                    count--;
                    tvcount.text=count.toString()
                    tot.text="₹"+(pp.substring(1).toInt()*count).toString()
                    db!!.updateNote(name,count.toString(),price,image.toString())
                }
            })
            delete.setOnClickListener(View.OnClickListener {
                //showAlert(position)
            })


        }
    }
    fun showAlert(position: Int){
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
        builder.setTitle("Remove Cart item")
        builder.setMessage("Are you sure want to delete the item")
                .setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, which -> // continue with delete
                            db =DatabaseHelper(mContext)
                            db?.deleteNote(position)
                            db.allAuth
                            onItemClick.onClick()
                            dialog.cancel()
                        })
                .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, which -> // continue with delete
                            dialog.cancel()
                        })

                .show()
    }
    interface OnDeleteItemClick {
        fun onClick()
    }
}