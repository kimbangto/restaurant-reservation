package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.common.DateHandler
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.service.ReservationService

// 사용자 입력을 받고 service에 전달
class ReservationController {

    private val reservationService = ReservationService()

    // 예약 관리자 시작
    fun startReservationManager() {
        println("예약 관리자 시작")

        while (true) {
            println("1.예약하기 2.예약현황 3.예약취소 4.예약변경 0.예약 관리자 종료")
            val input = ConsoleInput.consoleLine()

            when (input) {
                "1" -> makeReservation()
                "2" -> viewReservation()
                "3" -> deleteReservation()
                "4" -> updateReservation()
                "0" -> break
                else -> println("잘못된 입력입니다.")
            }
        }
    }

    // 예약하기 입력받을 정보 : 이름, 전화번호, 날짜, 인원수
    // 입력받은 데이터 검사해야함
    fun makeReservation() {
        println("예약 시작")

        println("예약자 이름을 입력해주세요")
        val name = ConsoleInput.consoleLine()

        println("예약자 전화번호를 입력해주세요(-를 빼고 입력)")
        val phoneNumber = ConsoleInput.consoleLine()

        println("예약 인원을 입력해주세요")
        val numberOfPerson = ConsoleInput.consoleLine()

        println("날짜를 선택해주세요")
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            print("${i + 1}.${weekList[i]} ")
        }
        println()

        val num = ConsoleInput.consoleLine()
        val date = weekList[num.toInt() - 1]

        val guest = reservationService.saveGuest(name, phoneNumber)
        val reservation = reservationService.saveReservation(guest, date, numberOfPerson.toInt())

        // vip 처리

        println("예약자 이름: ${reservation.guest.name}")
        println("예약자 번호: ${reservation.guest.phoneNumber}")
        println("예약 인원: ${reservation.numberOfPerson}")
        println("예약 날짜: ${reservation.date}")

        // 예약 완료 시 예약 현황 혹은 시작 화면으로 ?
    }

    // 예약 현황
    fun viewReservation() {
        // 예약상황을 날짜별로 모두 출력
        println("예약 현황")

        val reservationMap = reservationService.getReservation()

        for ((key, value) in reservationMap) {
            println("예약 번호 $key | 예약자 : ${value.guest.name} 예약자 연락처 : ${value.guest.phoneNumber} 예약 인원 ${value.numberOfPerson} 예약 날짜 : ${value.date} ")
        }
    }

    // 예약 취소
    fun deleteReservation() {
        // 예약 리스트 날짜순으로 보여주고 예약 번호 입력해 취소
        // 예약 취소가 가능하도록 map 불러오기
        println("예약 취소")

        val reservationMap = reservationService.getReservation()

        // 날짜순으로 정렬
        val sortedMap = sortByDate(reservationMap)

        println("취소할 예약 번호를 입력해주세요")
        for ((key, value) in sortedMap) {
            println("예약번호 : $key | 예약자 : ${value.guest.name} 연락처 : ${value.guest.phoneNumber} 예약인원 : ${value.numberOfPerson} 예약 날짜 : ${value.date}")
        }

        // 입력 검사해야함
        val deleteNum = ConsoleInput.consoleLine()
        println("예약 번호 $deleteNum 이 취소됩니다")
        reservationService.deleteReservation(deleteNum.toInt())

    }

    // 예약 변경
    fun updateReservation() {
        // 날짜 순으로 정렬된 예약 리스트 보여주고 변경할 예약 선택 후 예약 정보 변경
        // 변경 가능한 예약 정보는 날짜만?

        println("예약 변경")

        val reservationMap = reservationService.getReservation()

        // 날짜순으로 정렬
        val sortedMap = sortByDate(reservationMap)

        println("변경할 예약 번호를 입력해주세요")
        for ((key, value) in sortedMap) {
            println("예약번호 : $key | 예약자 : ${value.guest.name} 연락처 : ${value.guest.phoneNumber} 예약인원 : ${value.numberOfPerson} 예약 날짜 : ${value.date}")
        }

        val updateNum = ConsoleInput.consoleLine()

        println("날짜를 선택해주세요")
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            print("${i + 1}.${weekList[i]} ")
        }
        println()

        val num = ConsoleInput.consoleLine()
        val date = weekList[num.toInt() - 1]

        val updateReservation = reservationService.updateReservation(updateNum.toInt(), date)
        if (updateReservation != null) {
            println("${updateReservation.guest.name}님의 변경된 예약 날짜는 ${updateReservation.date} 입니다.")
        }

    }

    // 예약 map을 날짜에 따라 정렬
    // 어느 패키지에 가야할지?
    fun sortByDate(map: MutableMap<Int, Reservation>): MutableMap<Int, Reservation> {
        // entries는 map의 키, 값 쌍을 set 형태로 반환하며, filter 등의 함수를 사용 가능
        val entries = map.entries.sortedBy { entry -> entry.value.date }

        val sortedMap = mutableMapOf<Int, Reservation>()
        for (entry in entries) {
            println("${entry.key} : ${entry.value}")
            sortedMap[entry.key] = entry.value
        }

        return sortedMap
    }

}