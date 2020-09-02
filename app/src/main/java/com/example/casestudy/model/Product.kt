package com.example.casestudy.model

data class Product(
    val id: Int,
    val name: String,
    val desc: String,
    val image: String,
    val price: Price
) {
}