package com.sesac.restaurant.service

import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Reservation
import java.time.LocalDateTime

class ReservationService {

    val guestMap = mutableMapOf<String, Guest>()
    val reservationMap = mutableMapOf<Guest, Reservation>()

    // controller에서 받은 요청을 처리한다

    /* guest 저장 */
    fun saveGuest(name: String, phoneNumber: String): Guest {
        val guest = Guest(name, phoneNumber)

        guestMap[phoneNumber] = guest // repository 처리

        return guest
    }

    // reservation 저장
    fun saveReservation(guest: Guest, date: LocalDateTime, numberOfPeople: Int): Reservation {
        val reservation = Reservation(guest, date, numberOfPeople)

        reservationMap[guest] = reservation// repository 처리

        return reservation
    }

}