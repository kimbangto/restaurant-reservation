package com.sesac.restaurant.service

import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.model.Reservation
import com.sesac.restaurant.repository.GuestRepository

class GuestService {
    val guestRepository = GuestRepository.getInstance(TextFileIO.getInstance())

    /** guest 저장 */
    suspend fun saveGuest(name: String, phoneNumber: String): Guest {
        var guest = Guest(name, phoneNumber)

        if (guestRepository.findByPhoneNumber(phoneNumber) == null) {
            guestRepository.saveGuest(guest)
        } else {
            guest = guestRepository.findByPhoneNumber(phoneNumber)!!
        }

        return guest
    }

    /** vip로 변경 */
    suspend fun updateVip(reservationMap: MutableMap<Int, Reservation>, guest: Guest) {
        if (reservationMap.filter { it.value.guest == guest && it.value.isVisit }.count() >= 1) {
            guest.isVIP = true
            guestRepository.makeVIPByPhoneNumber(guest.phoneNumber)
        }
    }

    /** blackList로 변경 */
    suspend fun updateBlackList(reservationMap: Map<Int, Reservation>, noShowNum: Int) {
        for (reservation in reservationMap) {
            if (reservation.key == noShowNum) {
                guestRepository.makeBlackListByPhoneNumber(reservation.value.guest.phoneNumber)
                break
            }
        }
    }
}