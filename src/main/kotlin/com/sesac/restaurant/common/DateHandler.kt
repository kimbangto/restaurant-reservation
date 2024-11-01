package com.sesac.restaurant.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/* 날짜 처리 object 클래스 */
object DateHandler {

//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

//    fun getToday(): LocalDateTime {
//        val today = LocalDateTime.now()
//
//        return today
//    }
//
//    fun getOneWeekAgo(): LocalDateTime {
//        val date = LocalDateTime.now().plusWeeks(1)
//
//        return date
//    }

    /* 오늘부터 일주일(7일)의 리스트 반환 */
    fun getWeekList(): MutableList<LocalDate> {
        val weekList = mutableListOf(LocalDate.now())

        for (i: Int in 1..6) {
            weekList.add(weekList[0].plusDays(i.toLong()))
        }

        return weekList

    }

//    fun reservationDate(input: String): LocalDateTime {
//        val dateList: List<String> = input.split("-")
//
//        val date = LocalDateTime.of(dateList[0].toInt(), dateList[1].toInt(), dateList[2].toInt(), dateList[3].toInt(), dateList[4].toInt())
//
//        return date
//    }


}