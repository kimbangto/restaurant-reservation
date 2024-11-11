package model

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Reservation(val reservationNumber: Int, val guest: Guest, var visitDate: LocalDate, var numberOfPeople: Int)