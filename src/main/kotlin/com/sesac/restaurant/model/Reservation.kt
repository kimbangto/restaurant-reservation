package com.sesac.restaurant.model

import java.time.LocalDate

data class Reservation(val guest: Guest, var date: LocalDate, var numberOfPerson: Int, var isVisit: Boolean = true)