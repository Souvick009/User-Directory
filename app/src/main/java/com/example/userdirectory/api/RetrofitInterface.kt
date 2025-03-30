package com.example.userdirectory.api

import com.example.userdirectory.dataClass.User
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {
    @GET("users")
    suspend fun fetchUsers(): Response<List<User>>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>
}