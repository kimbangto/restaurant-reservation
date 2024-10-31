package com.sesac.restaurant.model

import java.time.LocalDateTime
import java.util.Date

data class Reservation(val guest: Guest, val date: LocalDateTime, val numberOfPerson: Int, var isVisit: Boolean = true) {}