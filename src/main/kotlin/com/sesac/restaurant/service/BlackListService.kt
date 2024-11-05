package com.sesac.restaurant.service

import com.sesac.restaurant.data.json.JsonFileIO
import com.sesac.restaurant.data.txt.TextFileIO
import com.sesac.restaurant.model.Guest
import com.sesac.restaurant.repository.GuestRepository

class BlackListService {
    private val guestRepository = GuestRepository.getInstance(JsonFileIO.getInstance())

    /** "블랙리스트를 가져오고 반환해주는 함수" */
    suspend fun getBlackList(): List<Guest> {
        return guestRepository.getBlackListMap().values.toList()
    }

    /** "제거할 블랙리스트의 인덱스값을 받아와 삭제하고 업데이트해주는 함수" */
    suspend fun updateBlackList(index: Int, status: Boolean): Boolean {
        val blackListGuest = getBlackList()

        return if (index in blackListGuest.indices) {
            val guest = blackListGuest[index]
            guestRepository.deleteBlackListByPhoneNumber(guest.phoneNumber)
            true
        } else {
            false
        }
    }
}