package com.example.mjprestaurant.model.table

data class Table(
    val id: String,
    val number: String,
    val numberOfCustomers: Int,
    val isOccupied: Boolean = false
)

data class TableLogin(
    val tableId: String,
    val numberOfCustomers: Int
)