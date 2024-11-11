package com.sesac.restaurant.service

import model.Reservation
import com.sesac.restaurant.repository.ReservationRepository
import java.time.LocalDate

class ReservationService(private val repository: ReservationRepository = ReservationRepository()) {

    /** 오늘부터의 예약 모두 반환 */
    private fun getReservation() = repository.getMap().filter { LocalDate.now() <= it.value.visitDate }

    /** 예약번호로 예약 반환 */
    fun findByReservationNumber(reservationNumber: Int) = repository.findReservationByReservationNumber(reservationNumber)

    /** 받은 전화번호로 되어있는 예약만 반환 */
    fun getMyReservation(phoneNumber: String) = getReservation().filter { it.value.guest.phoneNumber == phoneNumber }

    /** 오늘자 예약 반환 */
    fun getDayReservation() = repository.getMap().filter { it.value.visitDate == LocalDate.now() }

    /** 오늘로부터 일주일치 예약 반환 */
    fun getWeekReservation() = repository.getMap().filter {  LocalDate.now() <= it.value.visitDate && it.value.visitDate <= LocalDate.now().plusDays(6) }

    /** 예약번호 중 가장 큰 숫자 + 1 반환 */
    fun createNewReservationNumber(): Int {
        val map = repository.getMap()
        return if(map.keys.isEmpty()) 1
        else map.keys.max() + 1
    }

    /** 예약 저장 */
    fun makeReservation(reservation: Reservation) {
        repository.saveReservation(reservation)
    }

    /** 예약 삭제 */
    fun deleteByReservationNumber(reservationNumber: Int) = repository.deleteByReservationNumber(reservationNumber)

    /** 예약 변경 */
    fun updateByReservationNumber(reservationNumber: Int, date: LocalDate, numberOfPeople: Int) = repository.updateByReservationNumber(reservationNumber, date, numberOfPeople)
}