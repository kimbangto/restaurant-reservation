package controller

import common.DateHandler
import model.Guest
import model.Reservation
import service.GuestService
import com.sesac.restaurant.service.ReservationService
import service.TableService
import view.*


class ReservationManagementController(private val view: ReservationView = ReservationView(), private val tableService: TableService = TableService(), private val tableView: TableView = TableView(), private val reservationService: ReservationService = ReservationService(), private val guestService: GuestService = GuestService()) {

    /** 예약관리 */
    fun reservationManagement() {
        while (true) {
            when(promptForInput(view.MESSAGE_RESV_START_1)) {
                view.select_makeReservation -> makeReservation()
                view.select_showReservation -> showReservation()
                view.select_cancelReservation -> cancelReservation()
                view.select_updateReservation -> updateReservation()
                view.select_exit -> return
                else -> printCommonErr()
            }
        }
    }

    /** 예약하기 */
    private fun makeReservation() {
        // 이름 받기
        val name = promptForInput(view.MESSAGE_RESV_MAKE_1_1)
        if(name.isBlank()) printOutput(view.MESSAGE_RESV_MAKE_1_2)
        // 전화번호 받기
        val phoneNumber = getPhoneNumber()
        // 블랙리스트 검사
        if(guestService.isPhoneNumberBlacklisted(phoneNumber) == true) {
            promptForInput(view.MESSAGE_RESV_ISBLACKLIST)
            return
        }
        // 예약 인원 받기 (최대 8인)
        var numberOfPerson = promptForInput(view.MESSAGE_RESV_MAKE_3_1).toInt()
        while (!(numberOfPerson in 1..8)) {
            promptForInput(view.MESSAGE_RESV_MAKE_3_2)
            numberOfPerson = promptForInput(view.MESSAGE_RESV_MAKE_3_1).toInt()
        }
        // 날짜 받기
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            view.printLocalDate(i + 1, weekList[i])
        }
        var num = promptForInput(view.MESSAGE_RESV_MAKE_4_1).toInt()
        // 선택지 외의 입력을 받으면 반복
        while (!(num in 1..7)) {
            promptForInput(view.MESSAGE_RESV_MAKE_4_2)
            num = promptForInput(view.MESSAGE_RESV_MAKE_4_1).toInt()
        }
        val date = weekList[num - 1]

        // 테이블 선택
        var tableNum: Int
        while(true) {
            tableView.showTablesByDate(tableService.getTablesByDate(date), date)
            tableNum = promptForInput(tableView.select_table_number).toInt()
            if (tableService.selectTable(date, numberOfPerson, tableNum)) break
        }
        // 테이블에 예약 할당
        val reservationNumber = reservationService.createNewReservationNumber()
        val guest = Guest(name, phoneNumber)
        val reservation = Reservation(reservationNumber, Guest(name, phoneNumber), date, numberOfPerson)
        tableService.makeReservationTable(date, tableNum, reservation, null)
        guestService.saveGuest(guest)
        reservationService.makeReservation(reservation)
        tableService.makeReservationTable(date, tableNum, reservation, null)
        printOutput(view.MESSAGE_RESV_COMPLETE)
        println()
    }

    /** 예약현황 */
    private fun showReservation() {
        when(promptForInput(view.MESSAGE_RESV_VIEW_1)) {
            view.select_day_reservation -> viewDayReservation()
            view.select_week_reservation -> viewWeekReservation()
        }
    }

    /** 예약취소 */
    private fun cancelReservation() {
        val phoneNumber = getPhoneNumber()
        val myReservation = reservationService.getMyReservation(phoneNumber)
        if(myReservation.isEmpty()) {
            printOutput(view.MESSAGE_RESV_NO_RESERVATION)
            return
        }
        myReservation.forEach{ printOutput(view.printReservationMap(it.value)) }
        val reservationNumber = promptForInput(view.MESSAGE_RESV_DELETE_1).toInt()
        if(reservationService.findByReservationNumber(reservationNumber) == null) {
            printOutput(view.MESSAGE_RESV_NO_RESERVATION)
            return
        }
        tableService.deleteReservationFromTable(reservationService.findByReservationNumber(reservationNumber)!!)
        reservationService.deleteByReservationNumber(reservationNumber)
        printOutput(view.MESSAGE_RESV_DELETE_2)
    }

    /** 예약변경 */
    private fun updateReservation() {
        val phoneNumber = getPhoneNumber()
        val myReservations = reservationService.getMyReservation(phoneNumber)
        if(myReservations.isEmpty()) {
            printOutput(view.MESSAGE_RESV_NO_RESERVATION)
            return
        }
        myReservations.forEach{ printOutput(view.printReservationMap(it.value)) }
        val reservationNumber = promptForInput(view.MESSAGE_RESV_UPDATE_1).toInt()
        if(reservationService.findByReservationNumber(reservationNumber) == null) {
            printOutput(view.MESSAGE_RESV_NO_RESERVATION)
            return
        }
        // 예약 인원 받기 (최대 8인)
        var numberOfPerson = promptForInput(view.MESSAGE_RESV_UPDATE_3).toInt()
        while (!(numberOfPerson in 1..8)) {
            promptForInput(view.MESSAGE_RESV_MAKE_3_2)
            numberOfPerson = promptForInput(view.MESSAGE_RESV_UPDATE_3).toInt()
        }
        // 날짜 받기
        val weekList = DateHandler.getWeekList()
        for (i: Int in 0..weekList.size - 1) {
            view.printLocalDate(i + 1, weekList[i])
        }
        var num = promptForInput(view.MESSAGE_RESV_UPDATE_2).toInt()
        // 선택지 외의 입력을 받으면 반복
        while (!(num in 1..7)) {
            promptForInput(view.MESSAGE_RESV_MAKE_4_2)
            num = promptForInput(view.MESSAGE_RESV_UPDATE_2).toInt()
        }
        val date = weekList[num - 1]

        // 테이블 선택
        var tableNum: Int
        while(true) {
            tableView.showTablesByDate(tableService.getTablesByDate(date), date)
            tableNum = promptForInput(tableView.select_table_number).toInt()
            if (tableService.selectTable(date, numberOfPerson, tableNum)) break
        }
        // 테이블에 예약 할당
        val reservation = Reservation(reservationNumber, guestService.findByPhoneNumber(phoneNumber)!!, date, numberOfPerson)
        reservationService.makeReservation(reservation)
        tableService.makeReservationTable(date, tableNum, reservation, null)
        // 기존 예약 삭제
        tableService.deleteReservationFromTable(reservation)
        reservationService.deleteByReservationNumber(reservationNumber)
        printOutput(view.MESSAGE_RESV_UPDATE_4)
        println()
    }

    /** 연락처 포맷에 맞게 입력받기(010 이후 8자리 숫자) */
    private fun getPhoneNumber(): String {
        val phoneNumRegex = Regex("^010\\d{8}")

        while (true) {
            val phoneNum = promptForInput(view.MESSAGE_RESV_MAKE_2_1)

            if (phoneNumRegex.matches(phoneNum)) {
                return phoneNum
            } else {
                promptForInput(view.MESSAGE_RESV_MAKE_2_2)
            }
        }
    }

    /** 금일 예약 */
    private fun viewDayReservation() {
        val reservationMap = reservationService.getDayReservation()

        reservationMap.forEach{ println(view.printReservationMap(it.value)) }
    }

    /** 일주일 예약 */
    private fun viewWeekReservation() {
        val reservationMap = (reservationService.getWeekReservation())

        reservationMap.forEach{ println(view.printReservationMap(it.value)) }
    }
}