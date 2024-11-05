package com.sesac.restaurant.controller

import com.sesac.restaurant.common.ConsoleInput
import com.sesac.restaurant.common.DateHandler
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.service.GuestService
import com.sesac.restaurant.service.ReservationService
import com.sesac.restaurant.view.ResvInputView
import com.sesac.restaurant.view.ResvOutputView
import java.time.LocalDate

// 사용자 입력을 받고 service에 전달
class ReservationController {

    private val reservationService = ReservationService()
    private val tableController = TableManagementController()
    private val guestService = GuestService()

    /** 예약 관리 시작 */
    suspend fun startReservationManager() {
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_START_1)

        while (true) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_START_1)
            val input = ConsoleInput.consoleLine()

            when (input) {
                "1" -> makeReservation()
                "2" -> viewReservation()
                "3" -> deleteReservation()
                "4" -> updateReservation()
                "0" -> break
                else -> ResvOutputView.printMessage(ResvOutputView.MESSAGE_ERROR)
            }
        }
    }

    /** 예약하기 입력받을 정보 : 이름, 전화번호, 날짜, 인원수 */
    suspend fun makeReservation() {
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_MAKE_1)

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_1)
        val name = ConsoleInput.consoleLine()

//        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_2)
//        val phoneNumber = ConsoleInput.consoleLine()
        val phoneNumber = getPhoneNumber()

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_3_1)
        var numberOfPerson = ConsoleInput.consoleLine().toInt()
        // 8인 이하만 예약 가능하게
        while (!(numberOfPerson in 1..8)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_3_2)
            numberOfPerson = ConsoleInput.consoleLine().toInt()
        }

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_4_1)
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            ResvOutputView.printLocalDate(i + 1, weekList[i])
        }
        ResvOutputView.printSpace()

        var num = ConsoleInput.consoleLine().toInt()
        // 선택지 외의 입력을 받으면 반복
        while (!(num in 1..7)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_4_2)
            num = ConsoleInput.consoleLine().toInt()
        }

        val date = weekList[num - 1]

        val guest = guestService.saveGuest(name, phoneNumber)

        // Todo 테이블예약 마무리
        tableController.showTablesByDate(date)
        val tableNumber = tableController.selectTable(date)

        // 블랙리스트일 경우 예약 되지 않게
        if (!guest.isBlackList) {

            // vip 처리
            val reservationMap = reservationService.getReservation()
            guestService.updateVip(reservationMap, guest)

            val reservation = reservationService.saveReservation(guest, date, numberOfPerson)

            // 테이블 정보 저장
            if (tableNumber != null) {
                tableController.makeReservationTable(date, tableNumber, reservation)
            }

            ResvOutputView.printMessageString(ResvOutputView.MESSAGE_RESV_MAKE_NAME, reservation.guest.name)
            ResvOutputView.printMessageString(ResvOutputView.MESSAGE_RESV_MAKE_TEL, reservation.guest.phoneNumber)
            ResvOutputView.printMessageInt(ResvOutputView.MESSAGE_RESV_MAKE_NUMBEROFPERSON, reservation.numberOfPerson)
            ResvOutputView.printMessageString(ResvOutputView.MESSAGE_RESV_MAKE_DATE, reservation.date.toString())
        } else {
            ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_MAKE_BLACKLIST)
        }
    }

    /** 연락처 포맷에 맞게 입력 받는 기능 (010 이후 8자리 숫자) */
    fun getPhoneNumber(): String {
        val phoneNumRegex = Regex("^010\\d{8}")

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_2_1)
        while (true) {
            val phoneNum = ConsoleInput.consoleLine()

            if (phoneNumRegex.matches(phoneNum)) {
                return phoneNum
            } else {
                ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_2_2)
            }
        }
    }

    /** 예약 현황 */
    suspend fun viewReservation() {
        // 예약상황을 날짜별로 모두 출력
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_VIEW_1)

        // 오늘 예약, 일주일 예약, 노쇼 처리 선택
        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_VIEW)
        val input = ConsoleInput.consoleLine()
        when (input) {
            "1" -> viewDayReservation()
            "2" -> viewWeekReservation()
            "3" -> noShow()
            else -> ResvOutputView.printMessage(ResvOutputView.MESSAGE_ERROR)
        }
    }

    /** 금일 예약 */
    suspend fun viewDayReservation() {
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_VIEW_3)

        val reservationMap = reservationService.getReservation().filter { it.value.date == LocalDate.now() }

        for ((key, value) in reservationMap) {
            ResvOutputView.printMessageReservation(ResvOutputView.MESSAGE_RESV_PRINT, key, value)
        }
    }

    /** 일주일 예약 */
    suspend fun viewWeekReservation() {
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_VIEW_4)

        val reservationMap = reservationService.getReservation().filter { it.value.date >= LocalDate.now() }
        val sortedMap = sortByDate(reservationMap)

        for ((key, value) in sortedMap) {
            ResvOutputView.printMessageReservation(ResvOutputView.MESSAGE_RESV_PRINT, key, value)
        }
    }

    /** 노쇼 처리 */
    suspend fun noShow() {

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_NOSHOW_1)
        // 노쇼 고객은 오늘 예약에서 선택
        val reservationMap = reservationService.getReservation().filter { it.value.date == LocalDate.now() }

        for ((key, value) in reservationMap) {
            ResvOutputView.printMessageReservation(ResvOutputView.MESSAGE_RESV_PRINT, key, value)
        }

        var noShowNum = ConsoleInput.consoleLine().toInt()
        while (!reservationMap.keys.contains(noShowNum)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_NO_RESERVATION)
            noShowNum = ConsoleInput.consoleLine().toInt()
        }

        reservationService.updateIsVisit(noShowNum)
        guestService.updateBlackList(reservationMap, noShowNum)
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_ISBLACKLIST)
    }

    /** 예약 취소 */
    suspend fun deleteReservation() {
        // 예약 리스트 날짜순으로 보여주고 예약 번호 입력해 취소
        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_DELETE_1)

        val reservationMap = reservationService.getReservation().filter { it.value.date >= LocalDate.now() }

        // 날짜순으로 정렬
        val sortedMap = sortByDate(reservationMap)

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_DELETE_1)
        for ((key, value) in sortedMap) {
            ResvInputView.printMessageReservation(ResvInputView.MESSAGE_RESV_PRINT, key, value)
        }

        // 입력 검사해야함
        var deleteNum = ConsoleInput.consoleLine().toInt()
        while (!reservationMap.keys.contains(deleteNum)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_NO_RESERVATION)
            deleteNum = ConsoleInput.consoleLine().toInt()
        }
        ResvOutputView.printMessageInt(ResvOutputView.MESSAGE_RESV_DELETE_2, deleteNum)
        reservationService.deleteReservation(deleteNum)

    }

    /** 예약 변경 */
    suspend fun updateReservation() {
        // 날짜 순으로 정렬된 예약 리스트 보여주고 변경할 예약 선택 후 예약 정보 변경
        // 변경 가능한 예약 정보는 날짜만?

        ResvOutputView.printMessage(ResvOutputView.MESSAGE_RESV_UPDATE_1)

        val reservationMap = reservationService.getReservation().filter { it.value.date >= LocalDate.now() }

        // 날짜순으로 정렬
        val sortedMap = sortByDate(reservationMap)

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_UPDATE_1)
        for ((key, value) in sortedMap) {
            ResvInputView.printMessageReservation(ResvInputView.MESSAGE_RESV_PRINT, key, value)
        }

        var updateNum = ConsoleInput.consoleLine().toInt()

        while (!reservationMap.keys.contains(updateNum)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_NO_RESERVATION)
            updateNum = ConsoleInput.consoleLine().toInt()
        }

        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_UPDATE_2)

        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            ResvOutputView.printLocalDate(i + 1, weekList[i])
        }
        ResvOutputView.printSpace()

        var num = ConsoleInput.consoleLine().toInt()

        while (!(num in 1..7)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_4_2)
            num = ConsoleInput.consoleLine().toInt()
        }

        val date = weekList[num - 1]

        // 여기부터
        ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_UPDATE_3)
        var numberOfPerson = ConsoleInput.consoleLine().toInt()

        while (!(numberOfPerson in 1..8)) {
            ResvInputView.printMessage(ResvInputView.MESSAGE_RESV_MAKE_3_2)
            numberOfPerson = ConsoleInput.consoleLine().toInt()
        }

        reservationService.updateReservation(updateNum, date, numberOfPerson)
        // 여기까지 채연이가 추가했어요~!

    }

    /** reservation map 을 날짜에 따라 정렬 */
    fun sortByDate(map: Map<Int, Reservation>): MutableMap<Int, Reservation> {
        // entries는 map의 키, 값 쌍을 set 형태로 반환하며, filter 등의 함수를 사용 가능
        val entries = map.entries.sortedBy { entry -> entry.value.date }

        val sortedMap = mutableMapOf<Int, Reservation>()
        for (entry in entries) {
            sortedMap[entry.key] = entry.value
        }

        return sortedMap
    }

}