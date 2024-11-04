package com.sesac.restaurant.model

class Table(val tableNumber: Int, val numberOfSeats: Int, val reservation: Reservation? = null, var isPaid: Boolean = false) {
}