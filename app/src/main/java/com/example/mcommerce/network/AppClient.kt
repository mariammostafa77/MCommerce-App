package com.example.mcommerce.network

import com.example.mcommerce.auth.model.CustomerDetail
import com.example.mcommerce.auth.model.CustomerX
import com.example.mcommerce.home.model.BrandsModel
import com.example.mcommerce.model.AllProductsModel
import com.example.mcommerce.model.ProductDetails
import com.example.mcommerce.model.DiscountCodesModel
import retrofit2.Response

class AppClient : RemoteSourceInterface {

        companion object {
            private var instance: AppClient? = null
            fun getInstance(): AppClient {
                return instance ?: AppClient()
            }
        }

        override suspend fun getAllProducts(): AllProductsModel {
            val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
            val response = service?.getProducts()
            return response!!
        }

        override suspend fun getAllBrands(): BrandsModel {
            val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
            val response = service?.getBrands()
            return response!!
        }



    override suspend fun getBrandProducts(id:String): AllProductsModel {
        val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
        val response = service?.getBrandProducts(id)
        return response!!
    }

    override suspend fun getSpecificProduct(id: String): ProductDetails {
            val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
            val response = service?.getSpecificProduct(id)
            return response!!
        }

        override suspend fun getDiscountCodes(): DiscountCodesModel {
            val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
            val response = service?.getDiscountCodesFromNetwork()
            return response!!
        }

    override suspend fun postNewCustomer(customer: CustomerDetail): Response<CustomerDetail> {
        val service = RetrofitHelper.getRetrofit()?.create(ServiceApi::class.java)
        val response = service?.postNewCustomer(customer)
        return response!!
    }


}
