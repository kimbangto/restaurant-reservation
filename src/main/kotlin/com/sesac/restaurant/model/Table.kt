package model

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Table(val tableNumber: Int, val date: LocalDate, var reservation: Reservation?, var tableOrder: Map<String, Int>? = null) {

    var numberOfSeats: Int = 0

    init {
        numberOfSeats = when (tableNumber) {
            1, 2, 3 -> 2
            4, 5, 6 -> 4
            7, 8 -> 8
            else -> 0  // 기본값 설정
        }
    }
}