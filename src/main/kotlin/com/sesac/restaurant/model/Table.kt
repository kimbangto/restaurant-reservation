package com.sesac.restaurant.model

data class Table(val tableNumber: Int, val numberOfSeats: Int, val reservation: Reservation?, var isPaid: Boolean = false) {}