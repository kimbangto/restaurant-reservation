package com.sesac.restaurant.service

import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.repository.ReservationRepository
import java.time.LocalDate

class ReservationService {

    // controller에서 받은 요청을 처리한다

    val reservationRepository = ReservationRepository.getInstance(TextFileIO.getInstance())

    /** reservation 저장 */
    suspend fun saveReservation(guest: Guest, date: LocalDate, numberOfPeople: Int): Reservation {
        val reservation = Reservation(guest, date, numberOfPeople)

        val reservationMap = reservationRepository.getMap()

        val key = if (reservationMap.isEmpty()) {
            1
        } else {
            reservationMap.keys.max() + 1
        }

        reservationRepository.saveReservation(key, reservation)

        return reservation
    }

    /** reservationMap 리턴 */
    suspend fun getReservation(): MutableMap<Int, Reservation> {
        return reservationRepository.getMap()
    }

    /** 예약 삭제 */
    suspend fun deleteReservation(deleteNum: Int) {
        reservationRepository.deleteReservation(deleteNum)
    }

    /** 예약 변경 */
    suspend fun updateReservation(updateNum: Int, date: LocalDate) {
        reservationRepository.updateReservation(updateNum, date)
    }

    /** 노쇼 처리 */
    suspend fun updateIsVisit(noShowNum: Int) {
        reservationRepository.makeReservationAsNoShow(noShowNum)
    }


}