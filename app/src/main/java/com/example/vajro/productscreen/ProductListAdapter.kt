package com.example.vajro.productscreen

import Products
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vajro.MainActivity
import com.example.vajro.R
import com.example.vajro.database.DatabaseHelper


class ProductListAdapter  (val mContext: Context,val products: List<Products>,val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){
    lateinit var view: View
    private var db: DatabaseHelper? = null
    var count=1
    var select:List<String>?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.productlist, parent, false)
        db = DatabaseHelper(mContext)
        if (db!!.getCount() > 0) {
            val values = db!!.allAuth
            select=values.productid
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        holder.bindItems(products.get(position), view, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(objectmenu: Products, views: View, position: Int) {
            var productimage = view.findViewById<AppCompatImageView>(R.id.productimage)
            var product_heading = view.findViewById<AppCompatTextView>(R.id.tv_product_title)
            var product_price = view.findViewById<AppCompatTextView>(R.id.tv_price)
            var layout=view.findViewById<ConstraintLayout>(R.id.layout)
            var inclayout=view.findViewById<ConstraintLayout>(R.id.increment_layout)
            var add_btn=view.findViewById<AppCompatButton>(R.id.btn_add)
            var plus_btn=view.findViewById<AppCompatButton>(R.id.plus_btn)
            var minus_btn=view.findViewById<AppCompatButton>(R.id.minus_btn)
            var tvcount=view.findViewById<AppCompatTextView>(R.id.tv_count)
            var wishlistimage = view.findViewById<AppCompatImageView>(R.id.product_list_image_wishList)
            if (select!=null)
            for (i in 0 until select!!.size){
                if (objectmenu.product_id.equals(select!!.get(i))){
                    add_btn.visibility=View.GONE
                    inclayout.visibility=View.VISIBLE
                }
            }

            layout.setOnClickListener(View.OnClickListener {
                var intent = Intent(mContext, ProductdeatilActivity::class.java)
               intent.putExtra("image", products.get(position).image)
                intent.putExtra("title", products.get(position).name)
                intent.putExtra("price", products.get(position).price)
                intent.putExtra("detail", products.get(position).description)
                intent.putExtra("id", products.get(position).product_id)
                if (add_btn.visibility.equals(View.VISIBLE)){
                    intent.putExtra("isitemadded", true)
                }else{
                    intent.putExtra("isitemadded", false)
                }
                mContext!!.startActivity(intent)

            })
            wishlistimage.setOnClickListener(View.OnClickListener {
                if (wishlistimage.getBackground()==getDrawable(mContext,R.drawable.ic_wishlist_selected))
                {
                    wishlistimage.setBackgroundResource(R.drawable.ic_wishlist_unselected);
                }
                else {
                    wishlistimage.setBackgroundResource(R.drawable.ic_wishlist_selected);
                }
            })

            add_btn.setOnClickListener(View.OnClickListener {
                writedb(objectmenu.name,objectmenu.price,objectmenu.image.toString());
                add_btn.visibility=View.GONE
                inclayout.visibility=View.VISIBLE
                if (db!=null&&db!!.getCount() > 0) {
                    val values = db!!.allAuth
                    onItemClick.onClick(values.productid.size.toString())
                }else{
                    onItemClick.onClick("0")
                }
            })
            plus_btn.setOnClickListener(View.OnClickListener {
                count++;
                tvcount.text=count.toString()

            })
            minus_btn.setOnClickListener(View.OnClickListener {
                if (count>1){
                    count--;
                    tvcount.text=count.toString()
                }
            })

            Glide.with(mContext)
                    .load(objectmenu.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .dontAnimate().into(productimage)
            product_heading.text=objectmenu.name
            product_price.text=objectmenu.price
        }
        fun writedb(productid: String,price:String,image:String){
            try {
                db = DatabaseHelper(mContext)
                db!!.insertAuth(productid,count.toString(),price,image)

            } catch (e: Exception) {
            }
        }
    }
    interface OnItemClick {
        fun onClick(value: String?)
    }
}