package com.example.mcommerce.shopping_cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerce.R
import com.example.mcommerce.draftModel.DraftOrderX
import com.example.mcommerce.model.DiscountCode

class ShoppingCartAdapter : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>(){
    var userShoppingCartProducts:List<DraftOrderX> = ArrayList<DraftOrderX>()
    lateinit var context: Context
    var counter: Int = 1


    fun setUserShoppingCartProducts(context: Context, _userShoppingCartProducts:List<DraftOrderX>){
        this.context= context
        userShoppingCartProducts = _userShoppingCartProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_cart_item,parent,false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shoppingCartProductTitle.text = userShoppingCartProducts[position].line_items?.get(0)!!.title
        holder.shoppingCartProductPrice.append("  ${userShoppingCartProducts[position].line_items?.get(0)!!.price} EGP")
        holder.ShoppingCartProductQuantity.text = userShoppingCartProducts[position].line_items?.get(0)!!.quantity.toString()
        Glide.with(context).load(userShoppingCartProducts[position].note_attributes?.get(0)?.value).into(holder.shoppingCartItemImage)
        holder.shoppingCartIncreaseQuantity.setOnClickListener {
           // if (counter < 10){
            counter++
           // }
          //  else {
                counter = 10
         //   }
            holder.ShoppingCartProductQuantity.text = counter.toString()
            }
        holder.shoppingCartDecreaseQuantity.setOnClickListener {
            if(counter>1) { counter-- }
            else{ counter = 1 }
            holder.ShoppingCartProductQuantity.text = counter.toString()
        }
        holder.shoppingCartItem.setOnClickListener {

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
        val shoppingCartIncreaseQuantity : ImageView = itemView.findViewById(R.id.shoppingCartIncreaseQuantity)
        val shoppingCartDecreaseQuantity : ImageView = itemView.findViewById(R.id.shoppingCartDecreaseQuantity)
        val ShoppingCartProductQuantity : TextView = itemView.findViewById(R.id.ShoppingCartProductQuantity)

    }
}
