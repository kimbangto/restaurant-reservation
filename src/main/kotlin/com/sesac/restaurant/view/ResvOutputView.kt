package com.sesac.restaurant.view

import com.sesac.restaurant.model.Reservation
import java.time.LocalDate

object ResvOutputView {

    const val MESSAGE_RESV_START_1 = "예약 관리 시작"

    const val MESSAGE_RESV_MAKE_1 = "예약 시작"
    const val MESSAGE_RESV_MAKE_NAME = "예약자 이름: %s"
    const val MESSAGE_RESV_MAKE_TEL = "예약자 연락처: %s"
    const val MESSAGE_RESV_MAKE_NUMBEROFPERSON = "예약 인원: %d"
    const val MESSAGE_RESV_MAKE_DATE = "예약 날짜: %s"
    const val MESSAGE_RESV_MAKE_BLACKLIST = "블랙리스트입니다"

    const val MESSAGE_RESV_VIEW_1 = "예약 현황"
    const val MESSAGE_RESV_VIEW_3 = "금일 예약 현황"
    const val MESSAGE_RESV_VIEW_4 = "일주일 예약 현황"
    const val MESSAGE_RESV_ISBLACKLIST = "블랙리스트로 지정되었습니다"

    const val MESSAGE_RESV_DELETE_1 = "예약 취소"
    const val MESSAGE_RESV_DELETE_2 = "예약 번호 %s 이 취소됩니다"

    const val MESSAGE_RESV_UPDATE_1 = "예약 변경"

    const val MESSAGE_RESV_PRINT = "예약 번호 %d | 예약자 : %s | 예약자 연락처 : %s | 예약 인원 %d | 예약 날짜 : %s "

    const val MESSAGE_ERROR = "잘못된 입력입니다."

    fun printMessage(message: String) {
        println(message)
    }

    /** 한 줄 건너뛰기 */
    fun printSpace() {
        println()
    }

    /** n.yyyy-MM-dd 날짜 출력 */
    fun printLocalDate(num: Int, date: LocalDate) {
        print("${num}.${date} | ")
    }

    fun printMessageString(message: String, str: String) {
        val formattedMessage = String.format(
            message,
            str
        )

        println(formattedMessage)
    }

    fun printMessageInt(message: String, i: Int) {
        val formattedMessage = String.format(
            message,
            i
        )

        println(formattedMessage)
    }

    fun printMessageReservation(message: String, key: Int, reservation: Reservation) {
        val formattedMessage = String.format(
            message,
            key,
            reservation.guest.name,
            reservation.guest.phoneNumber,
            reservation.numberOfPerson,
            reservation.date.toString()
        )

        println(formattedMessage)
    }
}