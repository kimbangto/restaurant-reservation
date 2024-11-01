package com.sesac.restaurant.model

data class PaidTable(val table: Table, val order: Order, val reservation: Reservation)