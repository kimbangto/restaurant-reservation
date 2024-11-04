package com.sesac.restaurant.view

import com.sesac.restaurant.model.Reservation
import java.util.*

object ResvInputView {

    const val MESSAGE_RESV_START_1 = "1.예약하기 2.예약현황 3.예약취소 4.예약변경 0.예약 관리자 종료"

    const val MESSAGE_RESV_MAKE_1 = "예약자 이름을 입력해주세요"
    const val MESSAGE_RESV_MAKE_2_1 = "예약자 연락처를 입력해주세요 (예: 01012341234)"
    const val MESSAGE_RESV_MAKE_2_2 = "- 없이 형식에 맞게 입력해주세요 (예: 01012341234)"
    const val MESSAGE_RESV_MAKE_3 = "예약 인원을 입력해주세요"
    const val MESSAGE_RESV_MAKE_4 = "날짜를 선택해주세요"

    const val MESSAGE_RESV_VIEW = "1.금일 예약 현황 2.일주일 예약 현황 3.노쇼 처리"

    const val MESSAGE_RESV_NOSHOW = "노쇼된 예약의 예약 번호를 선택해주세요"

    const val MESSAGE_RESV_DELETE_1 = "취소할 예약 번호를 입력해주세요"

    const val MESSAGE_RESV_UPDATE_1 = "변경할 예약 번호를 입력해주세요"
    const val MESSAGE_RESV_UPDATE_2 = "변경할 날짜를 선택해주세요"

    const val MESSAGE_RESV_PRINT = "예약 번호 %d | 예약자 : %s | 예약자 연락처 : %s | 예약 인원 %d | 예약 날짜 : %s "

    fun printMessage(message: String) {
        println(message)
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