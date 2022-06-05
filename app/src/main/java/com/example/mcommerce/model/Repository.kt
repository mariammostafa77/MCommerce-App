package com.example.mcommerce.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mcommerce.auth.model.CustomerDetail
import com.example.mcommerce.auth.model.CustomerX
import com.example.mcommerce.home.model.BrandsModel
import com.example.mcommerce.network.RemoteSourceInterface
import retrofit2.Response

class Repository private constructor(var remoteSource: RemoteSourceInterface, var context: Context)
    : RepositoryInterface {

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            remoteSource: RemoteSourceInterface,
            context: Context
        ): Repository {
            return instance ?: Repository(
                remoteSource,context
            )
        }
    }
    override suspend fun getAllProducts(): AllProductsModel {
        return remoteSource.getAllProducts()
    }

    override suspend fun getAllBrands(): BrandsModel {
        return remoteSource.getAllBrands()
    }

    override suspend fun getBrandProducts(id:String): AllProductsModel {
        return remoteSource.getBrandProducts(id)
    }

    override suspend fun getSpecificProduct(id:String): ProductDetails {

        return remoteSource.getSpecificProduct(id)
    }

    override suspend fun getDiscountsCods(): DiscountCodesModel {
        return  remoteSource.getDiscountCodes()
    }

    override suspend fun postNewCustomer(customer: CustomerDetail): Response<CustomerDetail> {
        return  remoteSource.postNewCustomer(customer)
    }

}