package com.example.mcommerce.home.view

import android.R.attr
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.home.model.SmartCollection
import android.R.attr.right

import android.R.attr.left
import android.widget.LinearLayout











class BrandAdapter() : RecyclerView.Adapter<BrandAdapter.ViewHolder>(){
    var allBrands:List<SmartCollection> = ArrayList<SmartCollection>()
    lateinit var context: Context
    lateinit var communicator: Communicator

    fun setUpdatedData(allBrands:List<SmartCollection>, context: Context,communicator: Communicator){
        this.allBrands=allBrands
        this.context=context
        this.communicator=communicator
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView){
        val brandImg: ImageView = itemView.findViewById(R.id.brandImg)
        val brandCard:CardView = itemView.findViewById(R.id.brandCard)
        fun bind(data: SmartCollection){
            Log.i("TAG","from onBind ${allBrands[position]}")
            Glide.with(context).load(allBrands[position].image?.src).into(brandImg)
            brandCard.setOnClickListener(View.OnClickListener {
                allBrands[position].title?.let { it1 -> communicator.goFromBrandToCategories(it1) }
                Log.i("TAG","brand name from adapter ${allBrands[position].title}")

            })
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_layout,parent,false);
        val layoutParams = LinearLayout.LayoutParams(
            (parent.height * 0.5).toInt(),
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(20, 10, 10, 0)
        view.layoutParams = layoutParams
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return allBrands.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        allBrands.get(position).let { holder.bind(it) }
    }
}
