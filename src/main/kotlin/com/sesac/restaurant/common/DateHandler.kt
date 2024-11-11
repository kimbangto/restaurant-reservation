package common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateHandler {

    /** "yyyy-MM-dd" */
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /** 오늘부터 일주일(7일)의 리스트 반환 */
    fun getWeekList(): MutableList<LocalDate> {
        val weekList = mutableListOf(LocalDate.now())

        for (i: Int in 1..6) {
            weekList.add(weekList[0].plusDays(i.toLong()))
        }

        return weekList

    }


}