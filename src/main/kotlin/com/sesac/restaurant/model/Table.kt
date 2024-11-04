package com.sesac.restaurant.model

class Table(val tableNumber: Int, val numberOfSeats: Int, var reservation: Reservation? = null, var isPaid: Boolean = false) {
}