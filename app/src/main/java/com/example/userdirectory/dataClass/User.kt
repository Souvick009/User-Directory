package com.example.userdirectory.dataClass

data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val username: String? = null,
    val address: Address? = null,
    val phone: String,
    val website: String? = null,
    val company: Company? = null
)