package com.sesac.restaurant.model

import java.util.Date

data class Reservation(val guest: Guest, val table: Table, val date: Date, val numberOfPerson: Int, var isVisit: Boolean = true, var order: MutableList<Menu>) {}