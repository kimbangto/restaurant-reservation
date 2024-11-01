package com.sesac.restaurant.service

import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Reservation
import java.time.LocalDateTime

class ReservationService {

    // 키값을 정수로 guestMap <Long, Guest> 손님 번호, 손님 정보
    // reservationMap <Long, Reservation> 예약 번호, 예약 정보
    private val guestMap = mutableMapOf<Int, Guest>()
    private val reservationMap = mutableMapOf<Int, Reservation>()

    // controller에서 받은 요청을 처리한다

    /* guest 저장 */
    fun saveGuest(name: String, phoneNumber: String): Guest {
        val guest = Guest(name, phoneNumber)

        // key 값은 guestMap의 키들중 가장 큰 값
        val key = guestMap.keys.max()

        guestMap.put(key, guest)
        // repository 처리

        return guest
    }

    // reservation 저장
    // value를 list로 저장해서 guest의 예약 횟수를 쉽게 알 수 있게
    fun saveReservation(guest: Guest, date: LocalDateTime, numberOfPeople: Int): Reservation {
        val reservation = Reservation(guest, date, numberOfPeople)

        // key 값은 reservationMap의 키들중 가장 큰 값
        val key = reservationMap.keys.max()

        reservationMap.put(key, reservation)
        // repository 처리

        for (map in reservationMap) {
            println(map)
        }

        return reservation
    }

    // reservationMap 리턴
    fun getReservation(): MutableMap<Int, Reservation> {
        return reservationMap
    }

    // 예약 삭제
    fun deleteReservation() {
    }

}