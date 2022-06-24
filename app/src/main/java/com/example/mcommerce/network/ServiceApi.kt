  package com.example.mcommerce.network

import com.example.mcommerce.auth.model.Customer
import com.example.mcommerce.auth.model.CustomerDetail
import com.example.mcommerce.auth.model.CustomerX
import com.example.mcommerce.draftModel.DraftOrder
import com.example.mcommerce.draftModel.DraftOrderX
import com.example.mcommerce.draftModel.DraftResponse
import com.example.mcommerce.home.model.BrandsModel
import com.example.mcommerce.model.*
import com.example.mcommerce.orders.model.Orders
import com.example.mcommerce.model.currencies.CurrencyResponse
import com.example.mcommerce.model.currencies.convertor.CurrencyConverter
import com.example.mcommerce.orders.model.OrderResponse
import retrofit2.Response
import retrofit2.http.*
import java.util.*

  interface ServiceApi {
    @Headers(
        "Accept: application/json",
        "X-Shopify-Access-Token: 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",
    )
    @GET("products.json")
    suspend fun getProducts(): AllProductsModel
    @Headers(
        "Accept: application/json",
        "X-Shopify-Access-Token: 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",
    )
    @GET("smart_collections.json")
    suspend fun getBrands(): BrandsModel
    @Headers(
        "Accept: application/json",
        "X-Shopify-Access-Token: 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",
    )
    @GET("collections/"+"{id}"+"/products.json")
    suspend fun getBrandProducts(@Path("id") id: String?): AllProductsModel
      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
      )
      @GET("products/"+"{id}"+".json")
      suspend fun getSpecificProduct(@Path("id") id: String?): ProductDetails

      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
      )
      @GET("products.json")
      suspend fun getSubCategories(@Query("vendor") vendor: String?,
                                   @Query("product_type") productType: String?,
                                    @Query("collection_id") collectionId: String?): AllProductsModel

      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
      )
      @GET("price_rules/1089622311051/discount_codes.json")
      suspend fun getDiscountCodesFromNetwork(): DiscountCodesModel

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @POST("customers.json")
      suspend fun postNewCustomer(@Body customer: CustomerDetail):Response<CustomerDetail>
      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @POST("draft_orders.json")
      suspend fun postNewDraftOrder(@Body order: DraftOrder):Response<DraftOrder>

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @GET("customers.json?limit=250")
      suspend fun getCustomers(): Customer

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @PUT("customers/"+"{id}"+".json")
      suspend fun addNewCustomerAddress(@Path("id") id: String? , @Body customer: CustomerDetail): Response<CustomerDetail>

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @GET("customers/"+"{id}"+".json")
      suspend fun getUserInfo(@Path("id")id: String?): CustomerDetail

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @FormUrlEncoded
      @PATCH("customers/"+"{id}"+".json")
      suspend fun changeCustomerCurrency(@Path("id") id: String? ,@Field("currency") currency: String): Response<CustomerDetail>

      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",)
      @GET("draft_orders.json?limit=250")
      suspend fun getShoppingCartProducts(): DraftResponse

      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @DELETE("draft_orders/"+"{draft_order_id}"+".json")
      suspend fun deleteProductFromShoppingCart(@Path("draft_order_id") id: String?): Response<DraftOrder>
      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
      )
      @GET("customers/"+"{id}"+"/orders.json")
      suspend fun getOrders(@Path("id") id:String?): Orders
      @Headers(
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
          "Retry-After: 2.0",
          "Accept: application/json",
          "X-Shopify-Access-Token: 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"
      )
      @PUT("draft_orders/{id}.json")
      suspend fun updateDraftOrder(@Path("id") id: String? , @Body order: DraftOrder):Response<DraftOrder>

      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",)
      @GET("currencies.json")
      suspend fun getAllCurrencies(): CurrencyResponse

      // old
      // bvWIQqwc5PjLwYrSgElp83ZEktkQWLJB
      //
      @GET("convert?apikey=ZZBiC6W0KTnat8KLnoTRNad8YmkDWnVK&amount=1&from=EGP")
      suspend fun getCurrencyValue(@Query("to") to: String): CurrencyConverter

      @GET("convert?apikey=bvWIQqwc5PjLwYrSgElp83ZEktkQWLJB&amount=1&to=EGP")
      suspend fun getCurrencyValuesInEgp(@Query("from") from: String): CurrencyConverter

      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
          "Retry-After: 2.0",
          "X-Shopify-Shop-Api-Call-Limit: 40/40",
      )
      @POST("orders.json")
      suspend fun postNewOrder(@Body order: OrderResponse):Response<OrderResponse>

}

