package com.sesac.restaurant.service

import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Reservation
import java.time.LocalDate

class ReservationService {

    // 인터페이스 상속받아서 사용?

    // 키값을 정수로 guestMap <Long, Guest> 손님 번호, 손님 정보
    // 파일 입출력 시 사용할 map
    private val guestMap = mutableMapOf<String, Guest>()

    // reservationMap <Long, Reservation> 예약 번호, 예약 정보
    // 파일 입출력 시 사용할 map
    private val reservationMap = mutableMapOf<Int, Reservation>()

    // controller에서 받은 요청을 처리한다

    /* guest 저장 */
    fun saveGuest(name: String, phoneNumber: String): Guest {
        val guest = Guest(name, phoneNumber)

        guestMap.put(guest.phoneNumber, guest)
        // repository 처리

        return guest
    }

    // reservation 저장
    // value를 list로 저장해서 guest의 예약 횟수를 쉽게 알 수 있게
    fun saveReservation(guest: Guest, date: LocalDate, numberOfPeople: Int): Reservation {
        val reservation = Reservation(guest, date, numberOfPeople)

        // key 값은 reservationMap의 키들중 가장 큰 값 + 1
//        val key = reservationMap.keys.maxOrNull() ?: 0

        val key = if (reservationMap.isEmpty()) {
            1
        } else {
            reservationMap.keys.max() + 1
        }

        println("키 값 : $key")

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
    fun deleteReservation(deleteNum: Int) {
        reservationMap.remove(deleteNum)
    }

    // 예약 변경
    fun updateReservation(updateNum: Int, date: LocalDate): Reservation? {
        // ?.let 으로 null이 아닐때만 let 안의 코드 실행
        reservationMap[updateNum]?.let { reservation -> reservation.date = date }

        return reservationMap[updateNum]
    }


}