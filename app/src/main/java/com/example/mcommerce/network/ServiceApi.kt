  package com.example.mcommerce.network
import com.example.mcommerce.home.model.BrandsModel
import com.example.mcommerce.model.AllProductsModel
import com.example.mcommerce.model.DiscountCodesModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

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


    //// https://madalex20220.myshopify.com/admin/api/2022-04/price_rules/1089622311051/discount_codes.json
      @Headers(
          "Accept: application/json",
          "X-Shopify-Access-Token: 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",
      )
      @GET("price_rules/1089622311051/discount_codes.json")
      suspend fun getDiscountCodesFromNetwork(): DiscountCodesModel

}

//https://madalex20220.myshopify.com/admin/api/2022-04/collections/273053319307/products.json