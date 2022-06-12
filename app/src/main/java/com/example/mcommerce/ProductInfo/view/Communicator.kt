package com.example.mcommerce.ProductInfo.view

import com.example.mcommerce.auth.model.Addresse
import com.example.mcommerce.draftModel.LineItem
import com.example.mcommerce.draftModel.OrderPrices
import com.example.mcommerce.model.Product
import com.example.mcommerce.orders.model.Order

interface Communicator {
    fun passProductData(product:Product)
    fun goFromBrandToCategories(brandName:String)
    fun goToSearchWithID(id:String)
    fun goToSearchWithAllData(id:String,brandName:String,subCatName:String)

    fun goToUserAddresses(totalAmount: String)
    fun goToPaymentFromAddress(selectedAddress: Addresse , tatalAmount: String)
   // fun goToUserAddresses(totalAmount: String)
    fun goToUserAddresses(lineItems: ArrayList<LineItem> ,orderPrices: ArrayList<OrderPrices> )
    fun goToPaymentFromAddress(selectedAddress: Addresse , lineItems: ArrayList<LineItem> ,orderPrices: ArrayList<OrderPrices>)
    fun goToProductDetails(id:Long)


   // fun goToUserAddresses()
   // fun goToPaymentFromAddress(selectedAddress: Addresse)
    fun goToOrderDetails(selectedOrder: Order)
    fun goToUserAddresses()
    fun goToPaymentFromAddress(selectedAddress: Addresse)
    fun goToOrderDetails(selectedOrder: Order)

}