package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.common.DateHandler
import com.sesac.restaurant.service.ReservationService

// 사용자 입력을 받고 service에 전달
class ReservationController {

    private val reservationService = ReservationService()

    // 예약 관리자 시작
    fun startReservationManager() {
        println("예약 관리자")
        println("1.예약하기 2.예약현황 3.예약변경 4.예약취소 0.예약 관리자 종료")
        val input = ConsoleInput.consoleLine()

        when(input) {
            "1" -> makeReservation()
            "2" -> getReservation()
            "3" -> deleteReservation()
            "4" -> updateReservation()
            else -> println("시작 화면으로 돌아가기")
        }
    }

    // 예약하기 입력받을 정보 : 이름, 전화번호, 날짜, 인원수
    // 입력받은 데이터 검사해야함
    fun makeReservation() {
        println("예약 시작")

        println("예약자 이름을 입력해주세요")
        val name = ConsoleInput.consoleLine()

        println("예약자 전화번호를 입력해주세요(-를 빼고 입력")
        val phoneNumber = ConsoleInput.consoleLine()

        println("예약 인원을 입력해주세요(최대 n명)")
        val numberOfPerson = ConsoleInput.consoleLine()

        println("날짜를 선택해주세요")
        // 선택가능한 날짜 출력해주기 오늘부터 일주일? 내일부터?
        // 날짜 포맷 yyyy-MM-dd HH:mm ?
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            print("${i + 1}.${weekList[i].format(DateHandler.formatter)} ")
        }
        println()

        val num = ConsoleInput.consoleLine()
        val date = weekList[num.toInt()]

        val guest = reservationService.saveGuest(name, phoneNumber)
        val reservation = reservationService.saveReservation(guest, date, numberOfPerson.toInt())

        println("예약자 이름: ${reservation.guest.name}")
        println("예약자 번호: ${reservation.guest.phoneNumber}")
        println("예약 인원: ${reservation.numberOfPerson}")
        println("예약 날짜: ${reservation.date.format(DateHandler.formatter)}")

        // 예약 완료 시 예약 현황 혹은 시작 화면으로 ?
    }

    // 예약 현황
    fun getReservation() {

    }

    // 예약 취소
    fun deleteReservation() {

    }

    // 예약 변경
    fun updateReservation() {

    }

}