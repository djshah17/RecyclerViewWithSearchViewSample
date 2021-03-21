package com.example.recyclerviewwithsearchfiltersample.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Employee(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("username")
    val userName: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("address")
    val employeeAddress : EmployeeAddress? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("website")
    val website: String? = null
) : Serializable
