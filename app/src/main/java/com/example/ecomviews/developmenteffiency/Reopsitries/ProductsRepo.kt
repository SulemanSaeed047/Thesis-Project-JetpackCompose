package com.example.ecomviews.developmenteffiency.Reopsitries

import com.example.ecomviews.developmenteffiency.API.ModelClasses.ProductsList
import com.example.ecomviews.developmenteffiency.DataSources.ProductsDataSrc

class ProductsRepo() {

    val productDataSrc = ProductsDataSrc()
    suspend fun getAllProducts(filter:String): List<ProductsList>? {

        return productDataSrc.getAllProducts(filter)

    }
    suspend fun getAllCategories(): List<String>? {

        return productDataSrc.getAllCategories()

    }
}