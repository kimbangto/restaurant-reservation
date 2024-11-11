package service

import model.Guest
import repository.GuestRepository

class CustomerManagementService {
    private val guestRepository = GuestRepository()

    fun getVipList(): List<Guest> {
        return guestRepository.getGuestMap().values.filter { it.isVIP }
    }

    fun getBlackList(): List<Guest> {
        return guestRepository.getGuestMap().values.filter { it.isBlackList }
    }

    fun deleteBlackList(index: Int): Boolean {
        val blackListedGuests = getBlackList()

        if (index in blackListedGuests.indices) {
            val blackListGuest = blackListedGuests[index]
            guestRepository.removeBlackList(blackListGuest.phoneNumber)
            return true
        } else {
            return false
        }
    }
}