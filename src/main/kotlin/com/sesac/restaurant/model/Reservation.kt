package com.sesac.restaurant.model

import java.util.Date

data class Reservation(val guest: Guest, val date: Date, val numberOfPerson: Int, var isVisit: Boolean = true) {}