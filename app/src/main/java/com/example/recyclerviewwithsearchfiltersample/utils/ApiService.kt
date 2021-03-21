package com.example.recyclerviewwithsearchfiltersample.utils

import com.example.recyclerviewwithsearchfiltersample.models.Employee
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUsers(): Call<MutableList<Employee>>

}