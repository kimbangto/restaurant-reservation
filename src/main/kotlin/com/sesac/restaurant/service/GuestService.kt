package service

import model.Guest
import com.sesac.restaurant.repository.GuestRepository

class GuestService(private val repository: GuestRepository = GuestRepository()) {

    fun saveGuest(guest: Guest) = repository.saveGuest(guest)

    fun findByPhoneNumber(phoneNumber: String) = repository.findGuestByPhoneNumber(phoneNumber)

    fun isPhoneNumberBlacklisted(phoneNumber: String): Boolean? = repository.findGuestByPhoneNumber(phoneNumber)?.isBlackList

    fun isPhoneNumberVIP(phoneNumber: String): Boolean? = repository.findGuestByPhoneNumber(phoneNumber)?.isVIP
}