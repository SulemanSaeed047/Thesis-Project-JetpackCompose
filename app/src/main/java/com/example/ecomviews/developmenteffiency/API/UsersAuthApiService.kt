package com.example.ecomviews.developmenteffiency.API

import com.example.ecomviews.developmenteffiency.API.ModelClasses.LoginReq
import com.example.ecomviews.developmenteffiency.API.ModelClasses.LoginResp
import com.example.ecomviews.developmenteffiency.API.ModelClasses.SignUpUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsersAuthApiService {
    @PUT("users/{id}")
    fun updateUser(
        @Path("id") userId: Int,
        @Body user: SignUpUser
    ): Call<SignUpUser>

    @POST("auth/login")
    fun loginUser(
        @Body loginReq: LoginReq
    ): Call<LoginResp>

    @GET("users")
    fun getAllUsers(): Call<List<SignUpUser>>
}
