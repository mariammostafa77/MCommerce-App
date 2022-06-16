package com.example.mcommerce.shopping_cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerce.ProductInfo.view.Communicator
import com.example.mcommerce.R
import com.example.mcommerce.draftModel.DraftOrder
import com.example.mcommerce.draftModel.DraftOrderX
import com.example.mcommerce.me.viewmodel.SavedSetting
import com.example.mcommerce.model.DiscountCode

class ShoppingCartAdapter(var comminicator: Communicator,private val listener: OnShoppingCartClickListener) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>(){
   // var userShoppingCartProducts:List<DraftOrderX> = ArrayList<DraftOrderX>()
    var userShoppingCartProducts:List<DraftOrder> = ArrayList<DraftOrder>()
    lateinit var context: Context
    var counter: Int = 1

    fun setUserShoppingCartProducts(context: Context, _userShoppingCartProducts:List<DraftOrder>){
        this.context= context
        userShoppingCartProducts = _userShoppingCartProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_cart_item,parent,false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val price = userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.price.toString()
        val amount = SavedSetting.getPrice(price, context)

        holder.shoppingCartProductTitle.text = userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.name.toString()
        holder.shoppingCartProductPrice.text = ("Price: ${amount}")

        holder.ShoppingCartProductQuantity.text = userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.quantity.toString()
        Glide.with(context).load(userShoppingCartProducts[position].draft_order?.note_attributes?.get(0)?.value).into(holder.shoppingCartItemImage)
        holder.shoppingCartIncreaseQuantity.setOnClickListener {
            counter = userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.quantity!!.toInt()
            counter++
            holder.ShoppingCartProductQuantity.text = counter.toString()
            listener.onIncrementClickListener(userShoppingCartProducts[position])
        }
        holder.shoppingCartDecreaseQuantity.setOnClickListener {
            counter = userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.quantity!!.toInt()
            if(counter>1) { counter-- }
            else{ counter = 1 }
            holder.ShoppingCartProductQuantity.text = counter.toString()
            listener.onDecrementClickListener(userShoppingCartProducts[position])

        }
        holder.deleteProductImage.setOnClickListener {
            listener.onDeleteItemClickListener(userShoppingCartProducts[position])
        }

        holder.shoppingCartItem.setOnClickListener {
            comminicator.goToProductDetails(userShoppingCartProducts[position].draft_order?.line_items?.get(0)?.product_id!!)
        }

    }

    override fun getItemCount(): Int {
        return userShoppingCartProducts.size
    }

    inner class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        val shoppingCartItem : CardView = itemView.findViewById(R.id.shoppingCartItem)
        val shoppingCartItemImage : ImageView = itemView.findViewById(R.id.shoppingCartItemImage)
        val shoppingCartProductTitle : TextView = itemView.findViewById(R.id.shoppingCartProductTitle)
        val shoppingCartProductPrice : TextView = itemView.findViewById(R.id.shoppingCartProductPrice)
        val deleteProductImage : ImageView = itemView.findViewById(R.id.deleteProductImage)
        val shoppingCartIncreaseQuantity : ImageView = itemView.findViewById(R.id.shoppingCartIncreaseQuantity)
        val shoppingCartDecreaseQuantity : ImageView = itemView.findViewById(R.id.shoppingCartDecreaseQuantity)
        val ShoppingCartProductQuantity : TextView = itemView.findViewById(R.id.ShoppingCartProductQuantity)
       // val selectedItemCheckBox : CheckBox = itemView.findViewById(R.id.selectedItemCheckBox)

    }
}
