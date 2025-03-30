package com.example.userdirectory.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val jsonPlaceholder_URL = "https://jsonplaceholder.typicode.com"
    private const val reqRes_URL = "https://reqres.in/api/"

    private val jsonPlaceholderRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(jsonPlaceholder_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val reqResRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(reqRes_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val jsonPlaceholderApi: RetrofitInterface = jsonPlaceholderRetrofit.create(RetrofitInterface::class.java)
    val reqResApi: RetrofitInterface = jsonPlaceholderRetrofit.create(RetrofitInterface::class.java)
}