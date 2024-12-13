package com.example.ecomviews.developmenteffiency.DataSources

import com.example.ecomviews.developmenteffiency.API.ModelClasses.ProductsList
import com.example.ecomviews.developmenteffiency.API.ProductsApiService
import com.example.ecomviews.developmenteffiency.API.RetrofitHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException

class ProductsDataSrc() {
    suspend fun getAllProducts(filter:String): List<ProductsList>? {
        return suspendCancellableCoroutine { continuation ->
            val retrofit = RetrofitHelper.getInstance()
            val service = retrofit.create(ProductsApiService::class.java)

            val call: Call<List<ProductsList>> = if (filter == "All") {
                service.getAllProducts()
            } else {
                service.getProductsByCategory(filter)
            }
            call.enqueue(object : Callback<List<ProductsList>> {
                override fun onResponse(
                    call: Call<List<ProductsList>>,
                    response: Response<List<ProductsList>>,
                ) {
                    if (response.isSuccessful) {
                        val bookList = response.body()
                        continuation.resume(bookList, {})
                    } else {
                        continuation.resumeWithException(Exception("Network call failed"))
                    }
                }
                override fun onFailure(call: Call<List<ProductsList>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
            continuation.invokeOnCancellation { }
        }
    }

    suspend fun getAllCategories(): List<String>? {
        return suspendCancellableCoroutine { continuation ->
            val retrofit = RetrofitHelper.getInstance()
            val service = retrofit.create(ProductsApiService::class.java)

            val call = service.getAllCategories()
            call.enqueue(object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>,
                ) {
                    if (response.isSuccessful) {
                        val bookList = response.body()
                        continuation.resume(bookList, {})
                    } else {
                        continuation.resumeWithException(Exception("Network call failed"))
                    }
                }
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
            continuation.invokeOnCancellation { }
        }
    }
}